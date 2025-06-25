package org.shared.commands;

import org.server.collections.CollectionManager;
import org.shared.datatransferobjects.Request;
import org.shared.datatransferobjects.Response;

public class Exit extends Command{
    public Exit(){
        super("exit");
    }
    public Response execute(Request request){
        CollectionManager.getInstance().save();
        return new Response("Выход из программы");
    }
    
    public String getDescription(){
        return "завершить программу";
    }

}
