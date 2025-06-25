package org.shared.commands;

import java.io.BufferedReader;
import java.util.Stack;

import org.client.console.DefaultConsole;
//import org.shared.exceptions.EndOfFileException;

import org.shared.datatransferobjects.Request;
import org.shared.datatransferobjects.Response;

public class ScriptExecute extends Command {
    Stack<BufferedReader> readerStack = new Stack<>();
    Stack<String> pathStack = new Stack<>();

    String currentPath;
    BufferedReader currentReader = DefaultConsole.getConsole().getReader();

    public ScriptExecute() {
        super("executeScript");
    }

    public Response execute(Request request) {
        return new Response("");
    }



    public String getDescription() {
        return "прочитать и исполнить скрипт из указанного файла.";
    }

}
