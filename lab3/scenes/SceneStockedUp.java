package scenes;

import java.util.Objects;

import items.*;
import person.Me;
import person.Person;
import person.Sailors;
import places.Ship;
import utils.CountZeroException;
import utils.Sizes;

public class SceneStockedUp extends Scene{
    public SceneStockedUp() {
        super();
    }

    public String start(){
        Me me = (Me) this.persons.get("я");
        me.resetState();

        Carpet carpet = (Carpet) this.items.get("ячменных ковриг");
        carpet.setCount((int) (Math.random()*5));

        BigGlayPot bigGlayPot = (BigGlayPot) this.items.get("глиняный горшок");
        bigGlayPot.setSize(Sizes.LARGE);
        Rice rice = (Rice) this.items.get("риса");
        rice.checkTemperature();
        bigGlayPot.inside(rice);
        bigGlayPot.clarification("обычное мое блюдо");
        
        BottleOfRome bottleOfRome = (BottleOfRome) this.items.get("бутылочку рому");
        GoatInk goatInk = (GoatInk) this.items.get("козьей туши");
        goatInk.setCount(Math.random()*3 + 1);

        try{
            carpet.checkCount();
            carpet.clarification("точнее - лепешек");

            me.take(new Item[]{carpet, bigGlayPot, bottleOfRome, goatInk});
        }
        catch (CountZeroException e){
            me.take(new Item[]{bigGlayPot, bottleOfRome, goatInk});
        }

        Gun gun = (Gun) this.items.get("пороху и дроби");
        Goat goat = (Goat) this.items.get("коз");
        gun.why(Person.Actions.fire, goat);
        
        
        Clothing clothing = (Clothing) this.items.get("одежда");
        Overcoat overcoat = (Overcoat) this.items.get("шинели");
        Ship ship = (Ship) this.places.get("корабль");
        overcoat.setCount((int) (Math.random()*5));
        try{
            overcoat.checkCount();
            clothing.clarification(overcoat.getText()+" из упомянутых выше");
            me.take(new Item[]{gun, clothing});
        }catch (CountZeroException e){
            me.take(new Item[]{gun});
        }
        

        Chest chest = (Chest) this.items.get("сундуках");
        Sailors sailors = (Sailors) this.persons.get("матросы");
        chest.setOwner(sailors);
        chest.transported(me, ship);
        overcoat.turnUp(chest);

        return me.getText() + overcoat.getText() + "; ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SceneStockedUp scene = (SceneStockedUp) o;
        return persons.equals(scene.persons) & items.equals(scene.items) & places.equals(scene.places);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, items, places);
    }


    @Override
    public String toString(){
        return  String.format("SceneStockedUp{persons = %s, items = %s, places = %s}", persons, items, places);
        }
}
