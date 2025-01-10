package utils;

import items.Item;
import person.Person;

public class CountZeroException extends Exception{

    private final String objectName;

    public CountZeroException(Item item) {
        super("количество предмета = 0"); 
        this.objectName = item.getName();
    }

    public CountZeroException(Person person) {
        super("количество персонажей = 0"); 
        this.objectName = person.getName();
    }

    @Override
    public String getMessage() {
        return "Объект \"" + objectName + "\" не имеет количетва";
    }   
}
