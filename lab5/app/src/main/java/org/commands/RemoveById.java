package org.commands;

import org.collections.CollectionManager;
import org.console.DefaultConsole;
import org.exceptions.NotFoundException;
import org.models.Flat;

public class RemoveById extends Command{
    public RemoveById(){
        super("removeById");
    }

    public void execute(String args) {
        try{
            Flat flat = CollectionManager.getInstance().getById(Integer.parseInt(args));
            CollectionManager.getInstance().remove(flat);
        }catch (NotFoundException e){
            DefaultConsole.getConsole().printError("Не удалось удалить элемент по указанному id: элемент не найден");
        }
    }

    public String getDescription(){
        return "удалить элемент из коллекции по его id";
    }
}
