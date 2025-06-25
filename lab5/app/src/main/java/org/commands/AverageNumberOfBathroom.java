package org.commands;

import org.collections.CollectionManager;
import org.console.DefaultConsole;


public class AverageNumberOfBathroom extends Command{
    public AverageNumberOfBathroom(){
        super("AverageNumberOfBathrooms");
    }

    public void execute(String args) {
        try{
            
            Float AverageNumberOfBathroom = CollectionManager.getInstance().getAverageNumberOfBathrooms();
            DefaultConsole.getConsole().println("Среднее число ванных комнат в коллекции: " + AverageNumberOfBathroom);
        }catch (Exception e){}
    }

    public String getDescription() {
        return "вывести среднее значение поля numberOfBathrooms для всех элементов коллекции";
    }

    
}   
