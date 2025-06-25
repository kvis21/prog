package org.commands;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.nio.charset.StandardCharsets;

import org.Configuration;
import org.console.DefaultConsole;
//import org.exceptions.EndOfFileException;
import org.exceptions.RecursionDetectException;
import org.utils.CommandParser;

public class ScriptExecute extends Command {
    Stack<BufferedReader> readerStack = new Stack<>();
    Stack<String> pathStack = new Stack<>();

    String currentPath;
    BufferedReader currentReader = DefaultConsole.getConsole().getReader();

    public ScriptExecute() {
        super("executeScript");
    }

    public void execute(String args) {
        String filePath = Configuration.DATA_PATH + "/" + args;
        pathStack.push(currentPath);

        // Проверка на рекурсию
        if (pathStack.contains(filePath)) {
            throw new RecursionDetectException();
        }
        
        try {
            // Сохраняем текущий reader перед переключением
            readerStack.push(currentReader);
            
            // Создаем новый reader для скрипта
            currentReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), StandardCharsets.UTF_8));
            currentPath = filePath;
            // Устанавливаем новый reader в консоль
            DefaultConsole.getConsole().setReader(currentReader);
            
            runScript(filePath);
        } catch (IOException e) {
            DefaultConsole.getConsole().printError("Ошибка открытия файла: " + filePath);
        } finally {
            restorePreviousReader();
        }
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
            // В крайнем случае возвращаемся к консольному вводу
            currentReader = new BufferedReader(new InputStreamReader(System.in));
            readerStack.clear();
            pathStack.clear();
            DefaultConsole.getConsole().setReader(currentReader);
        }
    }

    public String getDescription() {
        return "прочитать и исполнить скрипт из указанного файла.";
    }

    private void runScript(String scriptPath) {
        try {
            String line;
            while ((line = currentReader.readLine()) != null) {
                String[] args = CommandParser.parse(line);
                String command = args[0];

                if (command.isEmpty()) continue;
                
                try {
                    CommandManager.getInstance().getCommand(command)
                        .execute(args[1]);
                } catch (IllegalArgumentException e) {
                    DefaultConsole.getConsole().printError("Несуществующая команда: " + command);
                }
            }
        } catch (IOException e) {
            DefaultConsole.getConsole().printError("Ошибка чтения файла: " + scriptPath);
        }
    }
}
