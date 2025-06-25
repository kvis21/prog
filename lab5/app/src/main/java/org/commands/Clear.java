package org.commands;

import org.collections.CollectionManager;

public class Clear extends Command{
    public Clear(){
        super("clear");
    }

    public void execute(String args) {
        CollectionManager.getInstance().clear();
    }

    public String getDescription(){
        return "очистить коллекцию";
    }
}
