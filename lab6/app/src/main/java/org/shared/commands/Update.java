package org.shared.commands;

import org.server.collections.CollectionManager;
import org.server.modelcreaters.FlatCreater;
import org.shared.datatransferobjects.Request;
import org.shared.datatransferobjects.Response;
import org.shared.exceptions.NotFoundException;
import org.shared.models.Flat;

public class Update extends Command implements Createable{
    public Update(){
        super("update");
    }

    public Response execute(Request request) {
        try{
            Flat flat = CollectionManager.getInstance().getById(Integer.parseInt(request.getArgs()));
            CollectionManager.getInstance().update(flat, request.getObject());
            return new Response("Элемент успешно обновлен");
        }catch (NotFoundException e){
            return new Response("Не удалось обновить элемент по указанному id: элемент не найден");
        }
    }

    public Flat create(){
        return new FlatCreater().build();
    }

    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }
}
