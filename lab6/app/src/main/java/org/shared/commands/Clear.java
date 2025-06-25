package org.shared.commands;

import org.server.collections.CollectionManager;
import org.shared.datatransferobjects.Request;
import org.shared.datatransferobjects.Response;


public class Clear extends Command{
    public Clear(){
        super("clear");
    }

    public Response execute(Request request) {
        CollectionManager.getInstance().clear();
        return new Response("Коллекция очищена");
    }

    public String getDescription(){
        return "очистить коллекцию";
    }
}
