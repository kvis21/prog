package org.commands;

import org.console.DefaultConsole;
import org.collections.CollectionManager;

public class Info extends Command{

    public Info(){
        super("info");
    }

    @Override
    public void execute(String args) {
        DefaultConsole.getConsole().println("Сведения о коллекции:");
        DefaultConsole.getConsole().println(" Тип: " + CollectionManager.getInstance().getCollectionType());
        DefaultConsole.getConsole().println(" Количество элементов: " + CollectionManager.getInstance().getColectionSize());
        DefaultConsole.getConsole().println(" Дата последнего сохранения: " + CollectionManager.getInstance().getLastSaveTime());
        DefaultConsole.getConsole().println(" Дата последней инициализации: " + CollectionManager.getInstance().getLastInitTime());
    }

    @Override
    public String getDescription(){
        return "вывести информацию о коллекции";
    }

}
