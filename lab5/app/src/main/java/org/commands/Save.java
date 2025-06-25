package org.commands;

import org.collections.CollectionManager;

public class Save extends Command{
    public Save(){
        super("save");
    }

    public void execute(String args) {
        CollectionManager.getInstance().save();
    }

    public String getDescription() {
        return "сохранить коллекцию в файл";
    } 
}
