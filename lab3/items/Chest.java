package items;

import java.util.Objects;

import person.Person;
import person.Sailors;
import places.Place;

public class Chest extends Item{
    private Person transportedBy;
    public Chest(){
        super("сундуках");
        this.owner = new Sailors();
    }

    public void transported(Person person, Place place){
        this.text = "перевезенных "+ person.getParentCase()+ " с " + place.getName()+ " в " + this.text;
        transportedBy = person;
        person.addItem(this);
    }

    public void setOwner(Person person) {
        this.name = person.getPossessiveCase() + " " + this.name;
        this.text = person.getPossessiveCase() + " " + this.text;
    }

    public void resetState(){
        this.text = this.name;
        this.owner = new Sailors();
        this.transportedBy = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chest item = (Chest) o;
        return name == item.name & owner.equals(item.owner) & weight == item.weight & place.equals(item.place) & transportedBy.equals(item.transportedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, weight, place, transportedBy);
    }


    @Override
    public String toString(){
        return  String.format("Chest{name = %s, owner = %s, weight = %s, place = %s, transportedBy = %s}", name, owner.getName(), weight, place.getName(), transportedBy.getName());
        }
}
