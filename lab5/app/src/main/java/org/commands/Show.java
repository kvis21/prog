package org.commands;

import org.collections.CollectionManager;
import org.console.DefaultConsole;

public class Show extends Command{
    public Show(){
        super("show");
    }

    @Override
    public void execute(String args) {
        DefaultConsole.getConsole().println(CollectionManager.getInstance().toString());
    }

    @Override
    public String getDescription(){
        return "вывести все элементы коллекции";
    }

}
