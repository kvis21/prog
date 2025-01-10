package items;

import java.util.Objects;

public class Provisions extends Item{
    public Provisions(){
        super("провизия");
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provisions item = (Provisions) o;
        return name == item.name & owner.equals(item.owner) & weight == item.weight & place.equals(item.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, weight, place);
    }


    @Override
    public String toString(){
        return  String.format("Provision{name = %s, owner = %s, weight = %s, place = %s}", name, owner.getName(), weight, place.getName());
        }

}
