package scenes;

import person.Me;
import person.Person;

import java.util.Objects;

import items.Shallow;
import places.Island;
import places.Ridge;
import places.Sea;

import utils.Distances;
import utils.Sizes;

public class SceneAboutIsland extends Scene{
    public SceneAboutIsland(){
        super();
    }

    public String start(){
        Me me = (Me) this.persons.get("я");
        Island island = (Island) this.places.get("остров");
        Sea sea = (Sea) this.places.get("море");
        Shallow shallow = (Shallow) this.items.get("отмель");
        Ridge ridge = (Ridge) this.places.get("гряда окал");
        ridge.issued(Distances.SIXMILES, sea);
        me.resetState();

        island.setOwner(me);
        island.setSize(Sizes.SMALL);
        me.approached(Island.PartOfIsland.EAST.getPart());
        shallow.stretch(1.5);
        me.see(new Ridge[]{ridge, Ridge.PartRidge.UNDERWATER.getPart(), Ridge.PartRidge.OVERWATER.getPart()},
                new Shallow[]{shallow});
        
        me.toGoAround(Ridge.PartRidge.BRAID.getPart(), Person.Actions.hook);
        return island.getText() + " "  + me.getText();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SceneAboutIsland scene = (SceneAboutIsland) o;
        return persons.equals(scene.persons) & items.equals(scene.items) & places.equals(scene.places);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, items, places);
    }


    @Override
    public String toString(){
        return  String.format("SceneAboutIsland{persons = %s, items = %s, places = %s}", persons, items, places);
        }

}
