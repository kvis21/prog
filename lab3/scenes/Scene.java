package scenes;

import items.Item;
import person.Person;
import places.Place;

import java.util.HashMap;
import java.util.Map;

public abstract class Scene {
    protected Map<String, Person> persons;
    protected Map<String, Item> items;
    protected Map<String, Place> places;

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
