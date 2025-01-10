package scenes;

import java.util.HashMap;

import items.Item;
import places.Place;
import person.Person;

public abstract class Scene {
    protected HashMap<String, Person> persons;
    protected HashMap<String, Item> items;
    protected HashMap<String, Place> places;

    public Scene() {
        persons = new HashMap<String, Person>();
        items = new HashMap<String, Item>();
        places = new HashMap<String, Place>();
    }
    public void addPerson(Person person) {
        persons.put(person.getName(), person);
    }
    public void addItem(Item item) {
        items.put(item.getName(), item);
    }
    public void addPlace(Place place) {
        places.put(place.getName(), place);
    }
    public abstract String start();

}
