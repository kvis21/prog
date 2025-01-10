package scenes;

import java.util.Objects;

import items.Clothing;
import items.Desire;
import items.Necessary;
import items.Provisions;
import person.Me;
import person.Person;
import places.Kingdom;
import utils.Sizes;

public class SceneAboutDesire extends Scene{
    public SceneAboutDesire() {
        super();
    }
    
    public String start(){
        Desire desire = (Desire) this.items.get("желание");
        Kingdom kingdom = (Kingdom) this.places.get("царство");
        Necessary necessary = (Necessary) this.items.get("необходимым");
        Provisions provisions = (Provisions) this.items.get("провизия");
        Clothing clothing = (Clothing) this.items.get("одежда");

        Me me = (Me) this.persons.get("я");
        me.resetState();

        kingdom.setSize(Sizes.SMALL);
        kingdom.setOwner(me);
        desire.toGetAcquaintedWith(kingdom);
        desire.prevailed();
        me.commit(Person.Actions.flight);
        necessary.rangeItemToItem(provisions, clothing);
        me.stockedUp(necessary);
        return desire.getText() + ", " + me.getText();  
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SceneAboutDesire scene = (SceneAboutDesire) o;
        return persons.equals(scene.persons) & items.equals(scene.items) & places.equals(scene.places);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, items, places);
    }


    @Override
    public String toString(){
        return  String.format("SceneAboutDesire{persons = %s, items = %s, places = %s}", persons, items, places);
        }
}
