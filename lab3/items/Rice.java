package items;

import java.util.Objects;

public class Rice extends Item{
    private int temperature;

    public Rice(int temperature){
        super("риса");
        this.temperature = temperature;
    }
    public void checkTemperature() {
        if (temperature > 70) {
            this.name = "поджаренного риса";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rice item = (Rice) o;
        return name == item.name & owner.equals(item.owner) & weight == item.weight & place.equals(item.place) & temperature == item.temperature;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, weight, place);
    }


    @Override
    public String toString(){
        return  String.format("Rice{name = %s, owner = %s, weight = %s, place = %s, temperature = %d}", name, owner.getName(), weight, place.getName(), temperature);
        }
    
}
