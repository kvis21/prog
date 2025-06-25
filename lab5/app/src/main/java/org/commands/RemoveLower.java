package org.commands;

import org.collections.CollectionManager;
import org.models.Flat;
import org.models.modelcreaters.FlatCreater;

public class RemoveLower extends Command{

    public RemoveLower() {
        super("removeLower");
    }

    public void execute(String args) {
       try{
            Flat flat = new FlatCreater().build();
            CollectionManager.getInstance().getCollection().removeIf(flat1 -> flat1.compareTo(flat) < 0);
        }catch (Exception e){} 
    }

    public String getDescription() {
        return "удалить из коллекции все элементы, меньшие, чем заданный";
    }

}
