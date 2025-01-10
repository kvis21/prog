package places;

import java.util.Objects;

public class Ship extends Place{
    public boolean isPart;

    public Ship(){
        super("корабль");
        this.isPart = false;
    }

    private Ship(String part) {
        super(part);
        this.isPart = true;
    }
    public void resetState(){
        this.text = this.name;
    }
    public enum ShipParts {
        BODY("корпус"),
        ENGINE("мотор"),
        MAST("мачта"),
        DECK("палуба"),
        STERN("корма");
    
        private Ship part;
        ShipParts(String part){
            this.part = new Ship(part);   
        }
        public Ship getPart() {
            return this.part;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship place = (Ship) o;
        return name == place.name & persons.equals(place.persons) & items.equals(place.items) & isPart == place.isPart;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, persons, items, isPart);
    }


    @Override
    public String toString(){
        return  String.format("Ship{name = %s, items = %s, persons = %s, isPart = %b}", name, persons, items, isPart);
        }
}

