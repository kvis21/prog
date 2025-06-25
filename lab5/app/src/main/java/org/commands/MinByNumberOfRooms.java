package org.commands;

import org.collections.CollectionManager;
import org.console.DefaultConsole;
import org.models.Flat;

public class MinByNumberOfRooms extends Command{

    public MinByNumberOfRooms() {
        super("minByNumberOfRooms");
    }
    public void execute(String args) {
        Flat flat = CollectionManager.getInstance().getMinByNumberOfRooms();
        DefaultConsole.getConsole().println(flat.toString());
    }

    public String getDescription(){
        return "вывести любой объект из коллекции, значение поля numberOfRooms которого является минимальным";
    }
}
