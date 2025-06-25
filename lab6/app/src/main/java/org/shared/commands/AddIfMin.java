package org.shared.commands;

import org.server.collections.CollectionManager;
import org.shared.datatransferobjects.Request;
import org.shared.datatransferobjects.Response;
import org.shared.models.Flat;
import org.server.modelcreaters.FlatCreater;

public class AddIfMin extends Command implements Createable{
    public AddIfMin() {
        super("addIfMin");
    }

    public Response execute(Request request) {
        try{
            Flat minFlat = CollectionManager.getInstance().getMinFlat();
            Flat flat = request.getObject();

            if (flat.compareTo(minFlat)<0){
                CollectionManager.getInstance().addElement(flat);
                return new Response("Элемент успешно добавлен в коллекцию");
            }
            
        }catch (Exception e){} 
        return new Response("Элемент не добавлен в коллекцию");

        
    }

    public Flat create(){
        return new FlatCreater().build();
    }

    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }
}
