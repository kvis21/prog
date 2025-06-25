package org.client.scripthandler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

import org.client.Configuration;
import org.client.UDPClient;
import org.client.console.DefaultConsole;
import org.shared.datatransferobjects.Request;
import org.shared.datatransferobjects.Response;
import org.shared.exceptions.RecursionDetectException;
import org.shared.utils.CommandParser;

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
        String args = request.getArgs().trim();
        String filePath = Configuration.DATA_PATH + "/" + args;
        
        
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
                    DefaultConsole.getConsole().print("Введите команду: ");
                    
                    Request request = CommandParser.parse(line);
                    
                    if (request.getCommandName().isEmpty()) continue;
                    Response response = client.sendRequestWithRetry(request);

                    if (response != null) {
                        DefaultConsole.getConsole().println(response.getMessage());
                    } else {
                        System.out.println("Сервер недоступен. Попробуйте позже.");
                    }

                } catch (Exception e) {
                    System.err.println("Ошибка: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            DefaultConsole.getConsole().printError("Ошибка чтения файла: " + scriptPath);
        }
    }

}