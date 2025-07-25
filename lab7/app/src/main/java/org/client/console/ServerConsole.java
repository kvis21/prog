package org.client.console;

import org.jline.reader.*;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.widget.AutosuggestionWidgets;
import org.shared.commandmanager.CommandManager;
import org.shared.exceptions.EndOfFileException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * JLine-based console implementation for input/output operations.
 * Supports reading from both terminal and files with advanced features.
 */
public class ServerConsole implements Console {
    private static ServerConsole instance = new ServerConsole();
    private BufferedReader reader;
    private LineReader lineReader;

    private ServerConsole() {
        try {
            Terminal terminal = TerminalBuilder.builder()
                    .system(true)
                    .build();


            List<String> suggestions = new ArrayList<>();
            suggestions.add("exit");
            suggestions.add("save");

            this.lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new StringsCompleter(suggestions))
                    .build();

            AutosuggestionWidgets widgets = new AutosuggestionWidgets(lineReader);
            widgets.enable();
            for (String commandName : CommandManager.getCommands().values().stream().map(command -> command.getName()).toList()) {
                this.lineReader.getHistory().add(commandName);
            }
            this.lineReader.getHistory().add("login");
            this.lineReader.getHistory().add("register");


        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize JLine terminal", e);
        }
    }

    public static ServerConsole getConsole() {
        return instance;
    }

    @Override
    public void print(String obj) {
        System.out.print(obj);
    }

    @Override
    public void println(String obj) {
        System.out.println(obj);
    }

    @Override
    public void printError(String errorMessage) {
        System.err.println(errorMessage);
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader newReader) {
        this.reader = newReader;
    }

    public String readline(String prompt) throws IOException {
        try {
            String line = lineReader.readLine(prompt).trim().toLowerCase();
            if (line == null) {

            }
            return line;
        } catch (UserInterruptException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("Read failed", e);
        }
    }

    @Override
    public String readline() throws IOException, EndOfFileException {
        return readline("server> ");
    }

    public boolean ready() {
        try {
            return reader != null && reader.ready();
        } catch (IOException e) {
            return false;
        }
    }
}
