package org.shared.commands;

import org.server.collections.CollectionManager;
import org.shared.dto.Request;
import org.shared.dto.Response;


public class Clear extends Command {
    public Clear() {
        super("clear");
    }

    public Response execute(Request request) {
        CollectionManager.getInstance().clear(request.getUser().userId());
        return new Response("Коллекция очищена");
    }

    public String getDescription() {
        return "очистить коллекцию";
    }
}
