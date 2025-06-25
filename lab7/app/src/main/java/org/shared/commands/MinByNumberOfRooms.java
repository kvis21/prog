package org.shared.commands;

import org.server.collections.CollectionManager;

import org.shared.dto.Request;
import org.shared.dto.Response;
import org.shared.models.Flat;

public class MinByNumberOfRooms extends Command{

    public MinByNumberOfRooms() {
        super("minByNumberOfRooms");
    }
    public Response execute(Request request) {
        Flat flat = CollectionManager.getInstance().getMinByNumberOfRooms();
        return new Response(flat.toString());
    }

    public String getDescription(){
        return "вывести любой объект из коллекции, значение поля numberOfRooms которого является минимальным";
    }
}
