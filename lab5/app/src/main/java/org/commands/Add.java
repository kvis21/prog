package org.commands;

import org.collections.CollectionManager;
import org.models.modelcreaters.FlatCreater;


public class Add extends Command{
    public Add(){
        super("add");
    }

    public void execute(String args){
        CollectionManager.getInstance().addElement(new FlatCreater().build());
    }

    public String getDescription(){
        return "добавить элемент в коллекцию";
    }

}
