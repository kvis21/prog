package person;

import java.util.ArrayList;
import java.util.Objects;

import items.Item;
import places.Place;
import utils.Times;

public class Me extends Person{
    private boolean isPart;
    

    public Me () {
        super("я", "мной", "мой", 21, 70);
        isPart = false;
        
    }
    public Me (String part) {
        super(part, "этой", "этой", 21, 70);
        isPart = true;
    }

    public enum Parts {
        HEAD("голова"),
        BODY("тело"),
        LEGS("ноги"),
        HANDS("руки"),
        EARS("уши"),
        EYES("глаза"),
        HAIR("волосы");

        private Me part;

        Parts (String part){
            this.part = new Me(part);
        }
        public Me getPart(){
            return this.part;
        }
    }

    public void strengthened(Item what, Place where){
        this.text += " укрепил на " + where.getText()+ " " + what.getText();
        items.remove(what);
    }

    public void undertake(Times when, Person.Actions what, Place where){
        this.text += " "+when.getTime() + " стал предпринимать " + what.getActions() + " по " + where.getText();
        this.setPlace(where);
    }
    
    public void wentOut(Times when){
        this.text += " "+when.getTime()+" выходил в " + this.place.getName();
    }
    public void tryToHold(Place place){
        this.text += ", стараясь держаться у " + place.getName();
    }

    public void commit(Person.Actions action){
        this.text += " решился совершить " + action.getActions();
    }

    public void stockedUp(Item item){
        this.text += ", запасся в дорогу " + item.getText();
    }
    public void take(Item[] items){
        ArrayList<String> stringItems = new ArrayList<String>();
        for (Item item : items) {
            stringItems.add(item.getText());
            this.items.add(item);
            item.setOwner(this);
        }
        this.text += " взял " + String.join(", ", stringItems);
    }

    public void intendedToUse (Item than, Item how){
        this.text = this.name + " предполагал пользоваться";
    }

    public void walkTO(){
        this.text = this.getText() + " "+getName() +  " отправился в путь";
    }

    public void reign(Times time){
        this.text = time.getTime() + " царствования или, если угодно, пленения";
    }

    public void passedThrough(Times time){
        this.text += " проездил " + this.getName() +" "+ time.getTime();
    }

    public void approached(Place place){
        this.text += " приблизился к " + place.getName();
    }

    public void see(Place[] places, Item[] items){
        ArrayList<String> stringPlaces = new ArrayList<String>();
        for (Place place : places) {
            stringPlaces.add(place.getText());
        }
        ArrayList<String> stringItems = new ArrayList<String>();
        for (Item item : items) {
            stringItems.add(item.getText());
        }
        this.text += " увидел " + String.join(", ", stringPlaces)+ ", " + String.join(", ", stringItems);
    }

    public void toGoAround (Place place, Person.Actions action){
        this.text += ", чтобы обогнуть " + place.getName() + " нужно " + action.getActions();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Me person = (Me) o;
        return name == person.name & parentCase == person.parentCase & possessiveCase == person.possessiveCase & place.equals(person.place) & items.equals(person.items) & age == person.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parentCase, possessiveCase, place, items, age);
    }


    @Override
    public String toString(){
        return  String.format("Person{name = %s, parentCase = %s, possessiveCase = %s, place = %s, items= %s, age= %d, isPart = %b}", name, parentCase, possessiveCase, place.getName(), items, age, isPart);
        }

}

