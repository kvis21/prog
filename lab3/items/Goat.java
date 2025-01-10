package items;

import java.util.Objects;

public class Goat extends Item{
    public Goat(){
        super("коз", 15);
    }

    public void resetState(){
        this.text = this.name;
        this.weight = 15;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goat item = (Goat) o;
        return name == item.name & owner.equals(item.owner) & weight == item.weight & place.equals(item.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, weight, place);
    }


    @Override
    public String toString(){
        return  String.format("Goat{name = %s, owner = %s, weight = %s, place = %s}", name, owner.getName(), weight, place.getName());
        }
}
