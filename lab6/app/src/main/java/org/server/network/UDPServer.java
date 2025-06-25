package org.server.network;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

import org.shared.datatransferobjects.Request;
import org.shared.datatransferobjects.Response;
import org.client.console.DefaultConsole;
import org.server.ServerConfiguration;
import org.server.collections.CollectionManager;
import org.shared.commandmanager.CommandManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UDPServer implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(UDPServer.class);
    private static final int BUFFER_SIZE = ServerConfiguration.BUFFER_SIZE;
    private final int port;
    private volatile boolean running;
    private DatagramChannel channel;
    private Selector selector;

    public UDPServer(int port) {
        this.port = port;
        this.running = true;
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
                        handleRequest(channel);
                    }
                }
            } catch (IOException e) {
                if (running) {
                    logger.error("Ошибка в основном цикле сервера", e);
                }
            }
            if (System.in.available() > 0) {
                String input = DefaultConsole.getConsole().readline();
                handleInput(input);
            }
        }
    }

    private void handleRequest(DatagramChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);

        if (clientAddress != null) {
            logger.info("Получен запрос от {}", clientAddress);
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
                ObjectInputStream ois = new ObjectInputStream(bais);

                Request request = (Request) ois.readObject();
                logger.debug("Десериализован запрос: {}", request.getCommandName());

                Response response = CommandManager.executeCommand(request);
                logger.debug("Сформирован ответ: {}", response.getMessage());

                byte[] responseData = response.serialize();
                ByteBuffer responseBuffer = ByteBuffer.wrap(responseData);
                channel.send(responseBuffer, clientAddress);
                logger.info("Ответ отправлен клиенту {}", clientAddress);

            } catch (ClassNotFoundException e) {
                logger.error("Ошибка десериализации запроса", e);
                sendError(channel, clientAddress, "Неверный формат запроса");
            } catch (Exception e) {
                logger.error("Ошибка обработки запроса", e);
                sendError(channel, clientAddress, "Ошибка сервера: " + e.getMessage());
            }
        }
    }

    private void sendError(DatagramChannel channel, InetSocketAddress clientAddress, String errorMessage) throws IOException {
        logger.warn("Отправка ошибки клиенту: {}", errorMessage);
        Response errorResponse = new Response(errorMessage);
        byte[] errorData = errorResponse.serialize();
        ByteBuffer errorBuffer = ByteBuffer.wrap(errorData);
        channel.send(errorBuffer, clientAddress);
    }

    @Override
    public void close() {
        running = false;
        logger.info("Запущена процедура остановки сервера");
        
        try {
            CollectionManager.getInstance().save();
            logger.info("Коллекция сохранена");

            if (selector != null) {
                selector.wakeup();
                selector.close();
            }
            if (channel != null) {
                channel.close();
            }
            logger.info("Ресурсы сервера освобождены");
        } catch (IOException e) {
            logger.error("Ошибка при закрытии сервера", e);
        }
        
        logger.info("Сервер остановлен");
    }

    public void loadCollection() {
        try {
            CollectionManager.getInstance().load();
            logger.info("Коллекция загружена");
        } catch (Exception e) {
            logger.error("Ошибка загрузки коллекции", e);
        }
    }

    private void handleInput(String input) {
        logger.debug("Обработка консольной команды: {}", input);
        switch (input) {
            case "exit":
                running = false;
                logger.info("Получена команда на завершение работы");
                break;
            case "save":
                CollectionManager.getInstance().save();
                logger.info("Коллекция сохранена по запросу из консоли");
                break;
            default:
                logger.warn("Получена неизвестная консольная команда: {}", input);
        }
    }
}