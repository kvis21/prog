package items;

import java.util.Objects;

import places.Place;

public class Desire extends Item{
    public Desire(){
        super("желание");
    }

    public void toGetAcquaintedWith(Place with){
        this.text += " ознакомиться с " + with.getText();
    }

    public void prevailed(){
        this.text = this.text+" превозмогло";
    }

    public void resetState(){
        this.text = this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Desire item = (Desire) o;
        return name == item.name & owner.equals(item.owner) & weight == item.weight & place.equals(item.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, weight, place);
    }


    @Override
    public String toString(){
        return  String.format("Desire{name = %s, owner = %s, weight = %s, place = %s}", name, owner.getName(), weight, place.getName());
        }

    
}
