package items;

import java.text.DecimalFormat;
import java.util.Objects;


public class Shallow extends Item{
    private String type;
    public Shallow() {
        super("отмель");
        this.type = "песчанная";
    }

    public void stretch(double length) {
        this.text = this.type +" "+this.text+ " тянулась миль на " + new DecimalFormat("#.#").format(length).replace(',', '.');
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shallow item = (Shallow) o;
        return name == item.name & owner.equals(item.owner) & weight == item.weight & place.equals(item.place) & type.equals(item.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, weight, place, type);
    }


    @Override
    public String toString(){
        return  String.format("Shallow{name = %s, owner = %s, weight = %s, place = %s, type = %s}", name, owner.getName(), weight, place.getName(), type);
        }
}
