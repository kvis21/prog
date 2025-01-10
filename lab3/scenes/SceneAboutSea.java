package scenes;

import java.util.Objects;

import person.Me;
import person.Person;
import places.Sea;
import utils.Times;

public class SceneAboutSea extends Scene{
    public SceneAboutSea() {
        super();
    }

    public String start(){
        Sea sea = (Sea) this.places.get("море");
        Me me = (Me) this.persons.get("я");
        me.resetState();

        me.undertake(Times.TIMETOIME, Person.Actions.walks, sea);
        sea.Open();
        me.wentOut(Times.NEVER);
        me.tryToHold(Sea.SeaParts.BAY.getPart());
        


        return me.getText();
    }

        @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SceneAboutSea scene = (SceneAboutSea) o;
        return persons.equals(scene.persons) & items.equals(scene.items) & places.equals(scene.places);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, items, places);
    }


    @Override
    public String toString(){
        return  String.format("SceneAboutSea{persons = %s, items = %s, places = %s}", persons, items, places);
        }
}
