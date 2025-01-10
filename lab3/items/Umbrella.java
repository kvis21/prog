package items;

import java.util.Objects;

import person.Person;
import places.Place;

public class Umbrella extends Item{
    private boolean open = false;

    public Umbrella(){
        super("зонтик", 0.6);
    }

    public void open(Place how){
        this.open = true;
        this.text += " раскрытый в виде " + how.getName();
    }

    public void open(Item how){
        this.open = true;
    }

    public void open(){
        this.open = true;
        this.text += "открытый " + this.name;
    }

    public boolean isOpened(){
        return this.open;
    }

    public void came(Person person){
        this.text = this.text + " приходился над " + person.getName();
    } 

    public void came(Place place){
        this.text = this.text + " приходился над " + place.getName();
    }

    public void protect(Person person, Item fromWhat, Place how){
        this.text = this.text + " защищал " + person.getParentCase() +" от "+ fromWhat.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Umbrella item = (Umbrella) o;
        return name == item.name & owner.equals(item.owner) & weight == item.weight & place.equals(item.place) & open == item.open;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, weight, place, open);
    }


    @Override
    public String toString(){
        return  String.format("Umbrella{name = %s, owner = %s, weight = %s, place = %s, open = %b}", name, owner.getName(), weight, place.getName(), open);
        }

}
