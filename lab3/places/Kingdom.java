package places;

import utils.Sizes;

import java.util.Objects;

import person.Person;

public class Kingdom extends Place{
    private Sizes size;
    private final boolean isPart;
    private Person owner;

    public enum KingdomParts {
        BORDERS("границы"),
        CASTLE("крепость"),
        TOWERS("башни"),
        WALLS("стены");

        private Kingdom part;
        KingdomParts(String part){
            this.part = new Kingdom(part);   
        }
        public Kingdom getPart() {
            return this.part;
        }

        public void partOf(Place place){
             this.part.text = this.part.getName() + place.getText();
        }
    }

    public Kingdom(){
        super("царство");
        isPart = false;
    }

    private Kingdom(String part){
        super(part);
        isPart = true;
    }

    public void resetState(){
        this.text = this.name;
        size = null;
        owner = null;
        persons.clear();
        items.clear();
    }

    public void setSize(Sizes size){
        this.size = size;
        this.text = size.getSize() + " " + this.text;
    }

    public void setOwner(Person owner){
        this.owner = owner;
        this.text = owner.getPossessiveCase() + " " + this.text;   
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kingdom place = (Kingdom) o;
        return name == place.name & persons.equals(place.persons) & items.equals(place.items) & size.equals(place.size) & owner.equals(place.owner) & isPart==place.isPart;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, persons, items, size, owner, isPart);
    }


    @Override
    public String toString(){
        return  String.format("Kingdom{name = %s, items = %s, persons = %s, size = %s, owner = %s, isPart = %b}", name, size.getSize(), owner.getName(), isPart);
        }
}
