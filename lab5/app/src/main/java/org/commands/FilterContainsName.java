package org.commands;

import java.util.List;

import org.collections.CollectionManager;
import org.console.DefaultConsole;
import org.models.Flat;

public class FilterContainsName extends Command{
    public FilterContainsName(){
        super("filterContainsName");
    }
    public void execute(String args){
        List<Flat> flats = CollectionManager.getInstance().filterContainsName(args);
        DefaultConsole.getConsole().println(flats.toString());
    }

    public String getDescription(){
        return "вывести элементы, значение поля name которых содержит заданную подстроку";
    }
}
