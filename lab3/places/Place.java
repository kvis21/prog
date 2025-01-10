package places;

import java.util.ArrayList;

import items.Item;
import person.Person;

public abstract class Place {
    protected String name;
    protected ArrayList<Person> persons; 
    protected ArrayList<Item> items;
    protected String text;
    
    public Place(String name) {
        this.name = name;
        this.text = name;
    }

    public String getName() {
        return this.name;
    }

    public void addPerson(Person person) {
        this.persons.add(person);
    }
    public void addItem(Item item) {
        this.items.add(item);
    }

    public ArrayList<Person> getPersons() {
        return this.persons;
    }
    public ArrayList<Item> getItems() {
        return this.items;
    }

    public String getText(){
        return this.text;
    }

    abstract public void resetState();

}
