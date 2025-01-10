package utils;

import items.Item;
import person.Person;

public class NotEnoughItemsException extends Exception{

    private final String objectName;

    public NotEnoughItemsException(Item item) {
        super("количество предмета = 0"); 
        this.objectName = item.getName();
    }

    public NotEnoughItemsException(Person person) {
        super("количество персонажей = 0"); 
        this.objectName = person.getName();
    }

    @Override
    public String getMessage() {
        return "Объект \"" + objectName + "\" не имеет количетва";
    }   
}
