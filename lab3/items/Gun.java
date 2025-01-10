package items;

import java.util.Objects;

import person.Person;

public class Gun extends Item{
    public Gun(){
        super("пороху и дроби");
    }
    
    public void why(Person.Actions action,Item item){
        this.text+=", чтобы " + action.getActions()+" в "+ item.getText();
    }

    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gun item = (Gun) o;
        return name == item.name & owner.equals(item.owner) & weight == item.weight & place.equals(item.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, weight, place);
    }


    @Override
    public String toString(){
        return  String.format("Gun{name = %s, owner = %s, weight = %s, place = %s}", name, owner.getName(), weight, place.getName());
        }
    
    
}
