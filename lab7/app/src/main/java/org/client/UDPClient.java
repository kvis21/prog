package org.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import org.client.console.DefaultConsole;
import org.client.console.JLineConsole;
import org.client.scripthandler.ScriptHandler;
import org.jline.reader.UserInterruptException;
import org.server.database.security.AuthUser;
import org.shared.configs.ServerConfiguration;
import org.shared.commandmanager.CommandManager;
import org.shared.commands.Createable;
import org.shared.commands.Exit;
import org.shared.commands.ScriptExecute;
import org.shared.dto.Request;
import org.shared.dto.Response;
import org.shared.dto.UserDTO;
import org.shared.exceptions.RecursionDetectException;
import org.shared.models.Flat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UDPClient implements AutoCloseable {
    private static final int BUFFER_SIZE = ServerConfiguration.BUFFER_SIZE;
    private static final int TIMEOUT_MS = ServerConfiguration.TIMEOUT;
    private static final int MAX_RETRIES = ServerConfiguration.MAX_RETRIES;

    
    private Logger logger = LoggerFactory.getLogger(UDPClient.class);

    private final DatagramChannel channel;
    private final InetSocketAddress serverAddress;
    private final ScriptHandler scriptHandler;
    private final AuthUser userAutherization;

    UserDTO user;

    public UDPClient(String host, int port) throws IOException {
        this.channel = DatagramChannel.open();
        this.channel.configureBlocking(true);
        this.serverAddress = new InetSocketAddress(host, port);
        this.scriptHandler = new ScriptHandler(this);
        this.userAutherization = new AuthUser(this);

    }

    public void startInteractiveMode() {
        logger.info("Запуск клиента. Подключение к серверу:" + serverAddress.getHostName() + "по порту " + serverAddress.getPort());
        user = authUser();

        if (user == null){
            logger.error("Авторизация пользователя не удалась. Завершение работы клиента...");
            return;
        }

        JLineConsole.getConsole().println("Клиент пользователя запущен. Введите команду (help - список команд):");
        while (true) {
            try {
                String inputLine = JLineConsole.getConsole().readline("Введите команду: ");

                String[] input = inputLine.split(" ", 2);
                String command = input.length > 0 ? input[0].toLowerCase() : null;
                String args = input.length > 1 ? input[1] : null;
                
                Request request = new Request(user, command, args);

                Response response = sendRequestWithRetry(request);
    
                if (response != null) {
                    JLineConsole.getConsole().println(response.getMessage());
                } else {
                    JLineConsole.getConsole().println("Сервер недоступен. Попробуйте позже.");
                }
    
            } catch (UserInterruptException e) {
                JLineConsole.getConsole().printError("Перехвачен сигнал выхода из клиента");
                break;
            } catch (RecursionDetectException e) {
                JLineConsole.getConsole().printError("При выполнении скрипта была замечена рекурсия");
            } catch (IOException e) {
                JLineConsole.getConsole().printError("Ошибка при отправке запроса: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                JLineConsole.getConsole().printError("Ошибка при чтении ответа от сервера: " + e.getMessage());
            }
        }
    }

    public Response sendRequestWithRetry(Request request) throws ClassNotFoundException{
        if (new Exit().getName().equals(request.getCommandName())){
            logger.info("Завершение работы клиента...");
            close(); 
            System.exit(0);
        }
        if (checkCreateable(request.getCommandName())){
            Flat flat = ((Createable) CommandManager.getCommand(request.getCommandName())).create();
            request = new Request(user, request.getCommandName(), request.getArgs(),  flat);
        }

        if (new ScriptExecute().getName().equals(request.getCommandName())){
            return scriptHandler.execute(request);
        }

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                return sendRequest(request);
            } catch (SocketTimeoutException e) {
                if (attempt < MAX_RETRIES) {
                    logger.warn("Таймаут соединения. Попытка " + (attempt + 1) + " из " + MAX_RETRIES);
                }
            } catch (IOException e) {
                System.err.println("Сетевая ошибка: " + e.getMessage());
                return null;
            }
        }
        return null;
    }

    public Response sendRequest(Request request) throws IOException, ClassNotFoundException {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            oos.writeObject(request);
            byte[] requestData = baos.toByteArray();

            ByteBuffer buffer = ByteBuffer.wrap(requestData);
            channel.send(buffer, serverAddress);

            ByteBuffer responseBuffer = ByteBuffer.allocate(BUFFER_SIZE);
            channel.socket().setSoTimeout(TIMEOUT_MS);
            SocketAddress responseAddress = channel.receive(responseBuffer);

            if (responseAddress != null) {
                ObjectInputStream ois = new ObjectInputStream(
                    new ByteArrayInputStream(responseBuffer.array(), 0, responseBuffer.position())
                );
                return (Response) ois.readObject();
            }

            return null;
        }
    }

    @Override
    public void close() {
        try {
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
            System.out.println("Клиентские ресурсы освобождены");
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии клиента: " + e.getMessage());
        }
    }

    public UserDTO getUser() {
        return user;
    }

    private boolean checkCreateable(String command){
        return CommandManager.getCommand(command) instanceof Createable;
    }
    


    private UserDTO authUser(){
            
        String signInMode = "";
        UserDTO user = null;

        while ((!signInMode.equals("register") || !signInMode.equals("login")) & (user == null)){
            try{
                String login;
                String password;

                JLineConsole.getConsole().println("Зарегистрируйтесь, введя команду 'register', или авторизуйтесь командой 'login'");
                signInMode = JLineConsole.getConsole().readline().trim();
                switch (signInMode) {
                    case "register":
                        DefaultConsole.getConsole().println("\n=== Регистрация нового пользователя ===");
                        while (true) {
                            
                            login = JLineConsole.getConsole().readline("Введите логин: ").trim();

                            if (login.isEmpty()) {
                                JLineConsole.getConsole().printError("Логин не может быть пустым");
                                continue;
                            } 

                            if (login.contains(" ")) {
                                JLineConsole.getConsole().printError("Логин не может содержать пробелы");
                                continue;
                            }

                            Response responseLogin = userAutherization.checkUser(login);
                            if (responseLogin.isExist()){
                                JLineConsole.getConsole().printError("Пользователь с таким логином уже существует");
                                continue;
                            }

                        while (true){
                            password = JLineConsole.getConsole().readline("Введите пароль: ").trim();
                            if (password.isEmpty()) {
                                JLineConsole.getConsole().printError("Пароль не может быть пустым");
                                continue;
                            }

                            Response responseUser = userAutherization.register(login, password);
                            return responseUser.getUser();
                        }
                    }
                    case "login":
                        while (true){
                            DefaultConsole.getConsole().println("\n=== Процесс авторизации пользователя ===");
                            login = JLineConsole.getConsole().readline("Введите логин: ").trim();
                            if (login.isEmpty()) {
                                JLineConsole.getConsole().printError("Логин не может быть пустым");
                                continue;
                            }

                            password = JLineConsole.getConsole().readline("Введите пароль: ").trim();
                            if (password.isEmpty()) {
                                JLineConsole.getConsole().printError("Пароль не может быть пустым");
                                continue;
                            }
                            
                            if (login.contains(" ")){
                                JLineConsole.getConsole().printError("Ошибка авторизации пользователя: неверный логин или пароль");
                                break;
                            }

                            Response responseUser = userAutherization.login(login, password);
                            if (responseUser.getUser() == null){
                                JLineConsole.getConsole().printError("Ошибка авторизации пользователя: неверный логин или пароль");
                                break;
                            }
                            return responseUser.getUser();
                        }
                        
                }
            }catch (UserInterruptException e){
                JLineConsole.getConsole().printError("Перехвачен сигнал выхода из клиента");
                return null;
            }catch (IOException e){
                JLineConsole.getConsole().println("ошибка ввода " + e.getMessage());
                return null;
            } catch (ClassNotFoundException e) {
                JLineConsole.getConsole().printError("Ошибка при чтении ответа от сервера: " + e.getMessage());
                return null;
            }
        }
        return user;   
    }
}


