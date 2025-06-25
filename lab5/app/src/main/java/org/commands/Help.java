package org.commands;

import java.util.Map;

import org.console.DefaultConsole;


public class Help extends Command {

    public Help() {
        super("help");
    }

    @Override
    public void execute(String args) {
        
        StringBuilder help = new StringBuilder();

        help.append("#".repeat(100)+"\n");
        for (Map.Entry<String, Command> entry : CommandManager.getCommands().entrySet()) {
            help.append(entry.getKey() + " - " + entry.getValue().getDescription()+"\n");
        }
        help.append("#".repeat(100));

        DefaultConsole.getConsole().println(help.toString());
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }
}

