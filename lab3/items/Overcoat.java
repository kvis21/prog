package items;

import interfaces.Countable;
import utils.NotEnoughItemsException;

import java.text.DecimalFormat;
import java.util.Objects;

public class Overcoat extends Item implements Countable{
    private int count;
    public Overcoat(){
        super("шинели");
    }

    public void setCount(double number) {
        this.count = (int) number;
        try{
            checkCount();
            this.text =  new DecimalFormat("#.##").format(number) + " " + this.text;
        } catch (NotEnoughItemsException e) {
            this.resetState();
        }
        
    }

    public double getCount() {
        return count;
    }

    public void checkCount() throws NotEnoughItemsException {
        if(count == 0){
            throw new NotEnoughItemsException(this);
        }
    }

    public void turnUp(Item where) {
        if (count != 0){
            this.text =" " + this.name + " оказались " + where.getText();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Overcoat item = (Overcoat) o;
        return name == item.name & owner.equals(item.owner) & weight == item.weight & place.equals(item.place) & count == item.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, weight, place, count);
    }


    @Override
    public String toString(){
        return  String.format("Overcoat{name = %s, owner = %s, weight = %s, place = %s, count = %3.2f}", name, owner.getName(), weight, place.getName(), count);
        }
}
