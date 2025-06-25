package org.shared.commands;

import org.server.collections.CollectionManager;
import org.shared.datatransferobjects.Request;
import org.shared.datatransferobjects.Response;
import org.shared.exceptions.NotFoundException;
import org.shared.models.Flat;

public class RemoveById extends Command{
    public RemoveById(){
        super("removeById");
    }

    public Response execute(Request request) {
        try{
            Flat flat = CollectionManager.getInstance().getById(Integer.parseInt(request.getArgs()));
            CollectionManager.getInstance().remove(flat);
            return new Response("Удален элемент с id: "+request.getArgs());
        }catch (NotFoundException e){
           return new Response("Не удалось удалить элемент по указанному id: элемент не найден");
        }
    }

    public String getDescription(){
        return "удалить элемент из коллекции по его id";
    }
}
