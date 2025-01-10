package scenes;

import java.util.Objects;

import person.Me;
import utils.Times;

public class ScenePassedThrought extends Scene{
    public ScenePassedThrought() {
        super();
    }
    public String start(){
        Me me = (Me) this.persons.get("я");
        me.passedThrough(Times.LONGERTHANEXPECT);
        return me.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScenePassedThrought scene = (ScenePassedThrought) o;
        return persons.equals(scene.persons) & items.equals(scene.items) & places.equals(scene.places);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, items, places);
    }


    @Override
    public String toString(){
        return  String.format("ScenePassedThrought{persons = %s, items = %s, places = %s}", persons, items, places);
        }
}
