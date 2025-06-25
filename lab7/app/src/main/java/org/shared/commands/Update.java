package org.shared.commands;

import org.server.collections.CollectionManager;
import org.server.modelcreaters.FlatCreater;
import org.shared.dto.Request;
import org.shared.dto.Response;
import org.shared.exceptions.NotFoundException;
import org.shared.models.Flat;

public class Update extends Command implements Createable{
    public Update(){
        super("update");
    }

    public Response execute(Request request) {
        try{
            if (request.getArgs()==null){
                return new Response("Не указан id элемента");
            }
            int id = Integer.parseInt(request.getArgs());
            Flat flat = CollectionManager.getInstance().getById(id);
            if (request.getUser().userId()!=flat.getUserId()) {
                return new Response("Нельзя обновить элемент другого пользователя");
            }
            
            Flat flatNew = request.getObject();
            flatNew.setUserId(request.getUser().userId());
            flatNew.setId(id);

            CollectionManager.getInstance().update(flat, flatNew);
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
