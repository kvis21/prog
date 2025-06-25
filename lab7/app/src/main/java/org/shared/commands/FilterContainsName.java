package org.shared.commands;

import java.util.List;

import org.server.collections.CollectionManager;
import org.shared.dto.Request;
import org.shared.dto.Response;
import org.shared.models.Flat;


public class FilterContainsName extends Command{
    public FilterContainsName(){
        super("filterContainsName");
    }

    public Response execute(Request request){
        List<Flat> flats = CollectionManager.getInstance().filterContainsName(request.getArgs());
        return new Response(flats.toString());
    }

    public String getDescription(){
        return "вывести элементы, значение поля name которых содержит заданную подстроку";
    }
}
