package places;

import java.util.Objects;

public class Tent extends Place{
    public Tent(){
        super("тент");
    }

    public void resetState(){
        this.text = this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tent place = (Tent) o;
        return name == place.name & persons.equals(place.persons) & items.equals(place.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, persons, items);
    }


    @Override
    public String toString(){
        return  String.format("Tent{name = %s, items = %s, persons = %s}", name, persons, items);
        }
}
