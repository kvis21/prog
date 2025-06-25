package org.shared.commands;

import org.server.collections.CollectionManager;
import org.server.modelcreaters.FlatCreater;
import org.shared.dto.Request;
import org.shared.dto.Response;
import org.shared.models.Flat;


public class Add extends Command implements Createable{
    public Add(){
        super("add");
    }

    public Response execute(Request request) {
        Flat flat = request.getObject();
        flat.setUserId(request.getUser().userId());
        CollectionManager.getInstance().addElement(flat);
        return new Response("Элемент успешно добавлен.");
    }

    public String getDescription(){
        return "добавить элемент в коллекцию";
    }

    public Flat create(){
        return new FlatCreater().build();
    }
}
