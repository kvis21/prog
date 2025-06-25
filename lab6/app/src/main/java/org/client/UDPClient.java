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
import org.client.scripthandler.ScriptHandler;
import org.server.ServerConfiguration;
import org.shared.commandmanager.CommandManager;
import org.shared.commands.Createable;
import org.shared.commands.Exit;
import org.shared.commands.ScriptExecute;
import org.shared.datatransferobjects.Request;
import org.shared.datatransferobjects.Response;
import org.shared.exceptions.RecursionDetectException;
import org.shared.models.Flat;
import org.shared.utils.CommandParser;

public class UDPClient implements AutoCloseable {
    private static final int BUFFER_SIZE = ServerConfiguration.BUFFER_SIZE;
    private static final int TIMEOUT_MS = ServerConfiguration.TIMEOUT;
    private static final int MAX_RETRIES = ServerConfiguration.MAX_RETRIES;

    private final DatagramChannel channel;
    private final InetSocketAddress serverAddress;
    private final ScriptHandler scriptHandler;

    public UDPClient(String host, int port) throws IOException {
        this.channel = DatagramChannel.open();
        this.channel.configureBlocking(true);
        this.serverAddress = new InetSocketAddress(host, port);
        this.scriptHandler = new ScriptHandler(this);
    }

    public void startInteractiveMode() {
        System.out.println("Клиент запущен. Введите команду (help - список команд):");
    
        while (true) {
            try {
                DefaultConsole.getConsole().print("Введите команду: ");
                String input = DefaultConsole.getConsole().readline();
    
                Request request = CommandParser.parse(input.trim());
                
                Response response = sendRequestWithRetry(request);
    
                if (response != null) {
                    DefaultConsole.getConsole().println(response.getMessage());
                } else {
                    DefaultConsole.getConsole().println("Сервер недоступен. Попробуйте позже.");
                }
    
            } catch (RecursionDetectException e) {
                DefaultConsole.getConsole().printError("При выполнении скрипта была замечена рекурсия");
            } catch (IOException e) {
                DefaultConsole.getConsole().printError("Ошибка при отправке запроса");
            } catch (ClassNotFoundException e) {
                DefaultConsole.getConsole().printError("Ошибка при чтении ответа от сервера");
            }
        }
    }

    public Response sendRequestWithRetry(Request request) throws ClassNotFoundException{
        if (new Exit().getName().equals(request.getCommandName())){
            DefaultConsole.getConsole().println("Завершение работы клиента...");
            close(); 
            System.exit(0);
        }
        if (checkCreateable(request.getCommandName())){
            Flat flat = ((Createable) CommandManager.getCommand(request.getCommandName())).create();
            request = new Request(request.getCommandName(), request.getArgs(),  flat);
        }
        if (new ScriptExecute().getName().equals(request.getCommandName())){
            return scriptHandler.execute(request);
        }
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                return sendRequest(request);
            } catch (SocketTimeoutException e) {
                if (attempt < MAX_RETRIES) {
                    System.out.println("Таймаут соединения. Попытка " + (attempt + 1) + " из " + MAX_RETRIES);
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

    private boolean checkCreateable(String command){
        return CommandManager.getCommand(command) instanceof Createable;
    }

}


