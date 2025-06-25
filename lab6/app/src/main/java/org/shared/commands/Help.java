package org.shared.commands;

import java.util.Map;

import org.shared.commandmanager.CommandManager;
import org.shared.datatransferobjects.Request;
import org.shared.datatransferobjects.Response;


public class Help extends Command {

    public Help() {
        super("help");
    }

    @Override
    public Response execute(Request request) {
        
        StringBuilder help = new StringBuilder();

        help.append("#".repeat(100)+"\n");
        for (Map.Entry<String, Command> entry : CommandManager.getCommands().entrySet()) {
            help.append(entry.getKey() + " - " + entry.getValue().getDescription()+"\n");
        }
        help.append("#".repeat(100));

        return new Response(help.toString());
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }
}

