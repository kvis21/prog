package person;

import java.util.Objects;
import interfaces.Countable;

public class Sailors extends Person implements Countable{
    private int count = 1;
    public Sailors(){
        super("матросы", "их", "матросских", 25, 60);
        this.weight = this.weight * this.count;
    }

    public void setCount(double number) {
        this.count = (int) number;
        this.weight = this.weight * this.count;
    }
    public double getCount() {
        return this.count;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sailors person = (Sailors) o;
        return name == person.name & parentCase == person.parentCase & possessiveCase == person.possessiveCase & place.equals(person.place) & items.equals(person.items) & age == person.age & count == person.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parentCase, possessiveCase, place, items, age, count);
    }


    @Override
    public String toString(){
        return  String.format("Person{name = %s, parentCase = %s, possessiveCase = %s, place = %s, items= %s, age= %d}", name, parentCase, possessiveCase, place.getName(), items, age, count);
        }
}
