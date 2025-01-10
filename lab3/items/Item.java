package items;

import person.Person;
import places.Place;
import places.UndefinedPlace;

public class Item {
    protected String name;
    protected Place place = new UndefinedPlace();
    protected String text;
    protected double weight = 0;
    protected Person owner = new Person();

    public Item(String name) {
        this.name = name;
        this.text = name;
    }

    public Item(String name, double weight) {
        this.name = name;
        this.text = name;
        this.weight = weight;
    }

    public String getName() {
        return this.name;
    }

    public void resetState(){
        this.text = "";
        this.owner = null;
        this.place = new UndefinedPlace();
        this.weight = 0;
    };

    public String getText(){
        return this.text;
    }

    public void setPlace(Place place){
        this.place = place;
    }
    public Place getPlace(){
        return this.place;
    }

    public void setOwner(Person owner){
        this.owner = owner;
    }

    public Person getOwner(){
        return this.owner;
    }
    public void changeWeight(double weight){
        this.weight += weight;
    }
    public double getWeight(){
        return this.weight;
    }

}
