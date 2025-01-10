package items;

import java.util.Objects;

public class BottleOfRome extends Item{
    private float volume;
    public BottleOfRome() {
        super("бутылочку рому");
        volume = (float) 0.7;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BottleOfRome item = (BottleOfRome) o;
        return name == item.name & owner.equals(item.owner) & weight == item.weight & place.equals(item.place) & volume == item.volume;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, weight, place, volume);
    }


    @Override
    public String toString(){
        return  String.format("BottleOfRome{name = %s, owner = %s, weight = %s, place = %s, %.2f}", name, owner.getName(), weight, place.getName(), volume);
        }
}
