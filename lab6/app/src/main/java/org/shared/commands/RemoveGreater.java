package org.shared.commands;

import org.server.collections.CollectionManager;
import org.shared.datatransferobjects.Request;
import org.shared.datatransferobjects.Response;
import org.shared.models.Flat;
import org.server.modelcreaters.FlatCreater;

public class RemoveGreater extends Command{
    public RemoveGreater() {
        super("removeGreater");
    }

    public Response execute(Request request) {
        try{
            Flat flat = new FlatCreater().build();
            CollectionManager.getInstance().getCollection().removeIf(flat1 -> flat1.compareTo(flat) > 0);
            return new Response("Элементы коллекции удалены");
        }catch (Exception e){
            return new Response("Не удалось удалить элементы коллекции");
        }
    }

    public String getDescription() {
        return "удалить из коллекции все элементы, превышающие заданный";
    }
}
