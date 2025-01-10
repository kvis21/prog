package person;

import java.util.ArrayList;
import java.util.Objects;

import items.Item;
import places.Place;
import places.UndefinedPlace;

public class Person {
    protected final String name;
    protected Place place = new UndefinedPlace();
    protected final String parentCase;
    protected final String possessiveCase;
    protected ArrayList<Item> items;
    protected String text;
    protected int age = 0;
    protected double weight = 0;
    public Person(){
        this.name = "";
        this.parentCase = "";
        this.possessiveCase = "";
        this.text = "";
        this.items = new ArrayList<Item>();
    }
    public Person (String name, String parentCase, String possessiveCase, int age, double weight){
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.parentCase = parentCase;
        this.possessiveCase = possessiveCase;
        this.text = name;
        this.items = new ArrayList<Item>();
    }

    public enum Actions {
        walks("прогулки"),
        flight("рейс"),
        fire("пострелять"),
        hook("сделать крюк");
    
        private String part;
    
        Actions (String part){
            this.part = part;
        }
        public String getActions(){
            return this.part;
        }
    }

    public String getName() {
        return this.name;
    }

    public String getPossessiveCase(){
        return this.possessiveCase;
    }

    public String getParentCase(){
        return parentCase;
    }

    public String getText() {
        return this.text;
    }

    public void resetState(){
        this.text = this.name;
        this.place = new UndefinedPlace();
        items.clear();
    }

    public void setPlace(Place place){
        this.place = place;
    }
    
    public Place getPlace(){
        return this.place;
    }

    public void addItem(Item item){
        this.items.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name == person.name & parentCase == person.parentCase & possessiveCase == person.possessiveCase & place.equals(person.place) & items.equals(person.items) & age == person.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parentCase, possessiveCase, place, items, age);
    }


    @Override
    public String toString(){
        return  String.format("Person{name = %s, parentCase = %s, possessiveCase = %s, place = %s, items= %s, age= %d}", this.name, parentCase, possessiveCase, this.place.getName(), this.items, this.age);
        }

}