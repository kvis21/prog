package items;

import java.util.Objects;

import interfaces.Clarification;
import utils.Sizes;

public class BigGlayPot extends Item implements Clarification{
    private Sizes size;
    private Item inside;
    public BigGlayPot(){
        super("глиняный горшок");
    }
    
    public void inside(Item item){
        this.text += " " +  item.getName();
        this.inside = item;
        this.changeWeight(item.getWeight());
    }

    public void setSize(Sizes size) {
        this.size = size;
        this.text = size.getSize() + " " +this.getText();
    }

    public void clarification(String clar){
        this.text += " (" + clar + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BigGlayPot item = (BigGlayPot) o;
        return name == item.name & owner.equals(item.owner) & weight == item.weight & place.equals(item.place) & size.equals(item.size) & inside.equals(item.inside);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, weight, place, size, inside);
    }


    @Override
    public String toString(){
        return  String.format("BigGlayPot{name = %s, owner = %s, weight = %s, place = %s, size = %s, inside = %s}", name, owner, weight, place, size.getSize(), inside.getName());
        }
}
