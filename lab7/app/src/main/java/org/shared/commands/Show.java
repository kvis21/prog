package org.shared.commands;

import org.server.collections.CollectionManager;
import org.shared.dto.Request;
import org.shared.dto.Response;
import org.client.console.DefaultConsole;

public class Show extends Command {
    public Show() {
        super("show");
    }

    @Override
    public Response execute(Request request) {
        DefaultConsole.getConsole().println(CollectionManager.getInstance().toString());
        return new Response(CollectionManager.getInstance().toString());
    }

    @Override
    public String getDescription() {
        return "вывести все элементы коллекции";
    }

}
