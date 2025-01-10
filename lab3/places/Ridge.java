package places;

import java.util.Objects;

import utils.Distances;

public class Ridge extends Place{
    private Distances length;
    private Boolean isPart = false;
    public Ridge (){
        super("гряда окал");
    }

    private Ridge(String part){
        super(part);
        this.isPart = true;
    }

    public enum PartRidge{
        UNDERWATER("часть подводных"),
        OVERWATER("часть над водой"),
        BRAID("коса");

        private Ridge part;

        PartRidge(String part){
            this.part = new Ridge(part);
        }

        public Ridge getPart(){
            return this.part;
        }
    }

    public void resetState(){
        this.text = this.name;
    }

    public void issued(Distances length, Place where){
        this.length = length;
        this.text += " выдавалась на " + length.getDistance() + " в "+ where.getText()+" ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ridge place = (Ridge) o;
        return name == place.name & persons.equals(place.persons) & items.equals(place.items) & length.equals(place.length) & isPart == place.isPart;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, persons, items, length, isPart);
    }


    @Override
    public String toString(){
        return  String.format("Ridge{name = %s, items = %s, persons = %s, length = %s, isPart = %b}", name, items, persons, length.getDistance(), isPart);
        }
    
}
