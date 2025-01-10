package items;

import java.util.Objects;

import interfaces.Countable;

public class GoatInk extends Item implements Countable{
    private double count;
    public GoatInk() {
        super("козьей туши", 1.5);
    }
    public void setCount(double number) {
        this.count = Math.round(number * 10.0) / 10.0;
        weight = weight * number;
        this.text = count + " "+this.text;
    }
    public double getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoatInk item = (GoatInk) o;
        return name == item.name & owner.equals(item.owner) & weight == item.weight & place.equals(item.place) & count == item.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, weight, place, count);
    }

    @Override
    public String toString(){
        return  String.format("Overcoat{name = %s, owner = %s, weight = %s, place = %s}", name, owner.getName(), weight, place.getName(), count);
        }
}
