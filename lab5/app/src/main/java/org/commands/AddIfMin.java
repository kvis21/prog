package org.commands;

import org.collections.CollectionManager;
import org.models.Flat;
import org.models.modelcreaters.FlatCreater;

public class AddIfMin extends Command{
    public AddIfMin() {
        super("addIfMin");
    }

    public void execute(String args) {
        try{
            Flat minFlat = CollectionManager.getInstance().getMinFlat();
            Flat flat = new FlatCreater().build();

            if (flat.compareTo(minFlat)<0){
                CollectionManager.getInstance().addElement(flat);
            }
        }catch (Exception e){}
    }

    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }
}
