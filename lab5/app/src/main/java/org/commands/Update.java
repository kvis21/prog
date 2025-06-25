package org.commands;

import org.collections.CollectionManager;
import org.console.DefaultConsole;
import org.exceptions.NotFoundException;
import org.models.Flat;

public class Update extends Command{
    public Update(){
        super("update");
    }

    public void execute(String args) {
        try{
            Flat flat = CollectionManager.getInstance().getById(Integer.parseInt(args));
            CollectionManager.getInstance().update(flat);
        }catch (NotFoundException e){
            DefaultConsole.getConsole().printError("Не удалось обновить элемент по указанному id: элемент не найден");
        }
    }

    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }
}
