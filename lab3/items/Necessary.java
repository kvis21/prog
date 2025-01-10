package items;

import java.util.Objects;

import interfaces.Ranged;
import utils.Pair;


public class Necessary extends Item implements Ranged{
    Pair<Item, Item> rangeItems;

    public Necessary (){
        super("необходимым");
    }

    public void rangeItemToItem(Item item1, Item item2) {
        this.text += ", начиная с " + item1.getName() + " и кончая " + item2.getName();
        rangeItems = new Pair<>(item1, item2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Necessary item = (Necessary) o;
        return name == item.name & owner.equals(item.owner) & weight == item.weight & place.equals(item.place) & rangeItems == item.rangeItems;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, weight, place, rangeItems);
    }

    @Override
    public String toString(){
        return  String.format("Necessary{name = %s, owner = %s, weight = %s, place = %s, rangeItems %s}", name, owner, weight, place, rangeItems.toString());
        }
}
