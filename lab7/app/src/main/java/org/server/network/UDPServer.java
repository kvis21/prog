package org.server.network;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.shared.dto.Request;
import org.shared.dto.Response;
import org.shared.dto.UserDTO;
import org.client.console.ServerConsole;
import org.jline.reader.UserInterruptException;
import org.shared.configs.ServerConfiguration;
import org.server.collections.CollectionManager;
import org.server.database.ConnectionManager;
import org.server.database.dbmanager.DBUserManager;
import org.server.database.security.PasswordUtil;
import org.shared.commandmanager.CommandManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UDPServer implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(UDPServer.class);
    private static final int BUFFER_SIZE = ServerConfiguration.BUFFER_SIZE;
    private static final int THREAD_POOL_SIZE = ServerConfiguration.THREAD_POOL_SIZE;
    private final int port;
    private final DBUserManager dbUserManager = new DBUserManager();
    private final ReadWriteLock collectionLock = new ReentrantReadWriteLock();
    private volatile boolean running;
    private DatagramChannel channel;
    private Selector selector;
    private Thread requestThread;
    private Thread consoleThread;
    private ExecutorService requestProcessingPool;

    public UDPServer(int port) {
        System.setProperty("file.encoding", "UTF-8");
        this.port = port;
        this.running = true;
        this.requestProcessingPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        logger.info("Сервер инициализирован для порта {}", port);
    }

    public void start() throws IOException {
        selector = Selector.open();
        channel = DatagramChannel.open();

        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress("0.0.0.0", port));
        channel.register(selector, SelectionKey.OP_READ);

        logger.info("Сервер запущен на порту {}", port);
        logger.debug("Селектор и канал инициализированы");

        startRequestThread();

        startConsoleThread();

        while (running) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void handleRequest() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);

            if (clientAddress != null) {
                logger.info("Получен запрос от {}", clientAddress);

                // Передаем обработку запроса в пул потоков
                requestProcessingPool.submit(() -> {
                    try {
                        ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
                        ObjectInputStream ois = new ObjectInputStream(bais);

                        Request request = (Request) ois.readObject();
                        logger.debug("Десериализован запрос");

                        Response response = processRequest(request);

                        new Thread(() -> {
                            try {
                                byte[] responseData = response.serialize();
                                ByteBuffer responseBuffer = ByteBuffer.wrap(responseData);
                                channel.send(responseBuffer, clientAddress);
                                logger.info("Ответ отправлен клиенту {}", clientAddress);
                            } catch (IOException e) {
                                logger.error("Ошибка отправки ответа клиенту {} - {}", clientAddress, e.getMessage());
                            }
                        }).start();

                    } catch (ClassNotFoundException e) {
                        logger.error("Ошибка десериализации запроса", e);
                        sendError(channel, clientAddress, "Неверный формат запроса");
                    } catch (Exception e) {
                        logger.error("Ошибка обработки запроса", e);
                        sendError(channel, clientAddress, "Ошибка сервера: " + e.getMessage());
                    }
                });
            }
        } catch (IOException e) {
            logger.error("Ошибка при получении запроса", e);
        }
    }

    private void startRequestThread() {
        requestThread = new Thread(() -> {
            while (running) {
                try {
                    int readyChannels = selector.select();
                    if (readyChannels == 0) continue;

                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectedKeys.iterator();

                    while (iter.hasNext() && running) {
                        SelectionKey key = iter.next();
                        iter.remove();

                        if (key.isReadable()) {
                            logger.debug("Обнаружено событие чтения");
                            handleRequest();
                        }
                    }
                } catch (IOException e) {
                    if (running) {
                        logger.error("Ошибка в основном цикле сервера", e);
                    }
                }
            }
        });
        requestThread.start();
    }

    private void startConsoleThread() {
        consoleThread = new Thread(() -> {
            while (running) {
                try {
                    String input = ServerConsole.getConsole().readline("server> ").trim();
                    if (input == null) { // Ctrl+D или закрытие консоли
                        running = false;
                        break;
                    }
                    handleInputProcess(input);
                } catch (UserInterruptException e) {
                    running = false;
                    logger.info("перехвачен ctrl + c. Сервер завершает работу");
                } catch (IOException e) {
                    if (running) {
                        logger.error("Ошибка чтения консольного ввода", e);
                        running = false;
                    }
                } catch (Exception e) {
                    running = false;
                }
            }
        });
        consoleThread.start();
    }

    private Response processRequest(Request request) {
        try {
            collectionLock.writeLock().lock();
            try {
                String commandName = request.getCommandName().trim().toLowerCase();

                if (commandName.equals("checkuser")) {
                    String login = request.getArgs();
                    return new Response(dbUserManager.existsByUsername(login));
                }

                if (commandName.equals("register")) {
                    String login = request.getArgs().split(" ", 2)[0];
                    String password = request.getArgs().split(" ", 2)[1];
                    var user = dbUserManager.saveUser(login, password);

                    if (user.isPresent()) {
                        return new Response(user.get());
                    }
                    return new Response("Ошибка при добавлении пользователя в БД");
                }

                if (commandName.equals("login")) {
                    String login = request.getArgs().split(" ", 2)[0];
                    String password = request.getArgs().split(" ", 2)[1];

                    var userOpt = dbUserManager.findByUsername(login);
                    if (userOpt.isEmpty()) {
                        return new Response("Пользователь не найден");
                    }

                    UserDTO user = userOpt.get();
                    if (!PasswordUtil.checkPassword(password, user.password())) {
                        return new Response("Неверный пароль");
                    }

                    return new Response(user);
                }

                Response response = CommandManager.executeCommand(request);

                return response;
            } finally {
                collectionLock.writeLock().unlock();
            }

        } catch (Exception e) {
            logger.error("Ошибка выполнения команды", e);
            return new Response("Ошибка сервера при выполнении команды");
        }
    }

    private void sendError(DatagramChannel channel, InetSocketAddress clientAddress, String errorMessage) {
        new Thread(() -> {
            try {
                logger.warn("Отправка ошибки клиенту: {}", errorMessage);
                Response errorResponse = new Response(errorMessage);
                byte[] errorData = errorResponse.serialize();
                ByteBuffer errorBuffer = ByteBuffer.wrap(errorData);
                channel.send(errorBuffer, clientAddress);
            } catch (IOException e) {
                logger.error("Ошибка отправки сообщения об ошибке", e);
            }
        }).start();
    }

    @Override
    public void close() {
        running = false;
        logger.info("Запущена процедура остановки сервера");

        try {
            collectionLock.writeLock().lock();
            try {
                CollectionManager.getInstance().save();
                logger.info("Коллекция сохранена");
            } finally {
                collectionLock.writeLock().unlock();
            }

            if (selector != null) {
                selector.wakeup();
                selector.close();
            }
            if (channel != null) {
                channel.close();
            }
            if (consoleThread != null) {
                consoleThread.interrupt();
            }
            if (requestThread != null) {
                requestThread.interrupt();
            }

            requestProcessingPool.shutdown();
            logger.info("Пул обработки запросов остановлен");

            logger.info("Ресурсы сервера освобождены");
        } catch (IOException e) {
            logger.error("Ошибка при закрытии сервера", e);
        }

        logger.info("Сервер остановлен");
    }

    public void loadCollection() {
        try {
            ConnectionManager.getInstance();
        } catch (SQLException e) {
        }

        collectionLock.writeLock().lock();
        try {
            CollectionManager.getInstance().load();
            logger.info("Коллекция загружена");
        } catch (Exception e) {
            logger.error("Ошибка загрузки коллекции", e);
        } finally {
            collectionLock.writeLock().unlock();
        }
    }

    private void handleInputProcess(String input) {
        logger.debug("Обработка консольной команды: {}", input);
        switch (input) {
            case "exit":
                running = false;
                logger.info("Получена команда на завершение работы");
                break;

            case "help":
                String helpMessage = "Доступные команды:\n" +
                        "exit - завершить работу сервера\n" +
                        "help - вывести справку по командам";
                ServerConsole.getConsole().println(helpMessage);
            default:
                logger.warn("Получена неизвестная консольная команда: {}", input);
        }
    }
}