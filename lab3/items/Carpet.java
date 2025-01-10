package items;

import java.text.DecimalFormat;
import java.util.Objects;

import interfaces.Clarification;
import interfaces.Countable;
import utils.CountZeroException;


public class Carpet extends Item implements Clarification, Countable{
    private int weight;
    private double count;
    public Carpet() {
        super("ячменных ковриг");
        weight = 1;
    }

    public void clarification(String clar) {
        this.text += " (" + clar + ")";
    }

    public void setCount(double number) {
        weight *= number;
        count = Math.round(number * 10.0) / 10.0;
        try{
            checkCount();
            this.text =  new DecimalFormat("#.##").format(number) + " " + this.text;
        }
        catch (CountZeroException e){
            this.resetState();
        }
    }

    public double getCount() {
        return count;
    }

    public void checkCount() throws CountZeroException{
        if(count == 0){
            throw new CountZeroException(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carpet item = (Carpet) o;
        return name == item.name & owner.equals(item.owner) & weight == item.weight & place.equals(item.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, weight, place);
    }


    @Override
    public String toString(){
        return  String.format("Carpet{name = %s, owner = %s, weight = %s, place = %s}", name, owner.getName(), weight, place.getName());
        }
}
