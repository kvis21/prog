package scenes;

import items.Umbrella;

import java.util.Objects;

import items.Sun;
import places.Ship;
import places.Tent;
import person.Me;


public class SceneWithUmbrella extends Scene{
    public SceneWithUmbrella() {
        super();
    }
    
    public String start(){
        Umbrella umbrella = (Umbrella) this.items.get("зонтик");
        Sun sun = (Sun) this.items.get("солнце");
        Ship ship = (Ship) this.places.get("корабль");
        Tent tent = (Tent) this.places.get("тент");
        Me me = (Me) this.persons.get("я");

        me.setPlace(ship);
        me.addItem(umbrella);

        umbrella.open(Ship.ShipParts.MAST.getPart());
        me.strengthened(umbrella, Ship.ShipParts.STERN.getPart());

        umbrella.resetState();
        
        umbrella.came(Me.Parts.HEAD.getPart());
        umbrella.protect(me, sun, tent);
    


        return me.getText() + ", так чтобы " + umbrella.getText();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SceneWithUmbrella scene = (SceneWithUmbrella) o;
        return persons.equals(scene.persons) & items.equals(scene.items) & places.equals(scene.places);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, items, places);
    }


    @Override
    public String toString(){
        return  String.format("SceneWithUmbrella{persons = %s, items = %s, places = %s}", persons, items, places);
        }
}
