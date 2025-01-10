package places;

import java.util.Objects;

import person.Person;
import utils.Sizes;

public class Island extends Place{
    private Person owner;
    private Sizes size;

    public Island(){
        super("остров");
    }

    private Island(String name){
        super(name);
    }

    public enum PartOfIsland{
        NORTH("Северная часть"),
        SOUTH("Южная часть"),
        EAST("Восточная часть"),
        WEST("Западная часть");

        private Island part;
        PartOfIsland(String part){
            this.part = new Island(part);
        }
        public Island getPart(){
            return this.part;
        }
    }

    public void resetState(){
        this.owner = null;
    }

    public void setOwner(Person person){
        this.owner = person;
        this.text = person.getPossessiveCase() + " " + this.getText();
    }

    public void setSize(Sizes size){
        this.size = size;
        this.text = this.getText() + " " + size.getSize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Island place = (Island) o;
        return name == place.name & persons.equals(place.persons) & items.equals(place.items) & size.equals(place.size) & owner.equals(place.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, persons, items, size, owner);
    }


    @Override
    public String toString(){
        return  String.format("Island{name = %s, items = %s, persons = %s, size = %s, owner = %s}", name, items, persons, size.getSize(), owner.getName());
        }
}
