package org.client.scripthandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Stack;

import org.shared.configs.ClientConfiguration;
import org.client.UDPClient;
import org.client.console.DefaultConsole;
import org.shared.dto.Request;
import org.shared.dto.Response;
import org.shared.exceptions.RecursionDetectException;

public class ScriptHandler {
    private final UDPClient client;

    Stack<BufferedReader> readerStack = new Stack<>();
    Stack<String> pathStack = new Stack<>();

    String currentPath;
    BufferedReader currentReader = DefaultConsole.getConsole().getReader();

    public ScriptHandler(UDPClient client){
        this.client = client;
    }

    public Response execute(Request request) {
        if (request.getArgs()==null) return new Response("Необходимо указать путь к файлу.");
        if (ClientConfiguration.DATA_PATH==null) return new Response("Необходимо указать системную переменную DATA_FILE_PATH - путь к папке с файлами скриптов.");

        String filename = request.getArgs().trim();
        String filePath = Paths.get(ClientConfiguration.DATA_PATH, filename)
            .toAbsolutePath()
            .normalize()
            .toString();

        if (!new File(filePath).exists()) return new Response("не удалось найти файл по указанному пути: " + filePath);
        
        
        if (pathStack.contains(filePath)) {
            throw new RecursionDetectException();
        }
        pathStack.push(currentPath);

        try {
            
            readerStack.push(currentReader);
            
            currentReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), StandardCharsets.UTF_8));
            currentPath = filePath;
            
        
            DefaultConsole.getConsole().setReader(currentReader);
            
            runScript(filePath);
            restorePreviousReader();
            
        } catch (IOException e) {
            return new Response("Ошибка открытия файла: " + filePath);
        }
        return new Response("Выполнение скрипта "+ filePath + " завершено.");
    }

    private void restorePreviousReader() {
        try {
            if (!readerStack.isEmpty()) {
                // Восстанавливаем предыдущий reader
                currentReader = readerStack.pop();
                currentPath = pathStack.pop();
                DefaultConsole.getConsole().setReader(currentReader);
            }
        } catch (Exception e) {
            DefaultConsole.getConsole().printError("Ошибка восстановления reader");
            resetToDefault();
        }
    }

    private void resetToDefault() {
        readerStack.clear();
        pathStack.clear();
        currentReader = new BufferedReader(new InputStreamReader(System.in));
        DefaultConsole.getConsole().setReader(currentReader);
    }

    private void runScript(String scriptPath) {
        try {
            String line;
            while ((line = currentReader.readLine()) != null) {
                try {
                    String[] input = line.trim().split(" ", 2);
                    String command = input.length > 0 ? input[0] : null;
                    String args = input.length > 1 ? input[1] : null;

                    Request request = new Request(client.getUser(), command, args);
                    
                    if (request.getCommandName().isEmpty()) continue;
                    Response response = client.sendRequestWithRetry(request);

                    if (response != null) {
                        DefaultConsole.getConsole().println(response.getMessage());
                    } else {
                        System.out.println("Сервер недоступен. Попробуйте позже.");
                    }
                
                } catch (RecursionDetectException e) {
                    resetToDefault();
                    throw new RecursionDetectException();
                } catch (Exception e) {
                    System.err.println("Ошибка: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            DefaultConsole.getConsole().printError("Ошибка чтения файла: " + scriptPath);
        }
    }

}