import items.*;
import person.*;
import places.*;
import scenes.*;
import utils.*;

public class Main {
    public static void main (String[] args) {

        Story story = new Story();

        Umbrella umbrella = new Umbrella();
        Me me = new Me();
        Ship ship = new Ship();
        Sea sea = new Sea();
        Kingdom kingdom = new Kingdom();
        Island island = new Island();

        Scene scene = new SceneWithUmbrella();
        scene.addItem(umbrella);
        scene.addItem(new Sun());
        scene.addPlace(ship);
        scene.addPlace(new Tent());
        scene.addPerson(me);
        String sentence1 = scene.start();
        
        scene = new SceneAboutSea();
        scene.addPerson(me);
        scene.addPlace(sea);
        String sentence2 = scene.start();

        scene = new SceneAboutDesire();
        scene.addPlace(kingdom);
        scene.addItem(new Desire());
        scene.addItem(new Necessary());
        scene.addItem(new Provisions());
        scene.addItem(new Clothing());
        scene.addPerson(me);
        String sentence3 = scene.start();

        scene = new SceneStockedUp();
        scene.addPerson(new Me()); 
        scene.addPerson(new Sailors());
        scene.addPlace(ship);
        scene.addItem(new BigGlayPot());
        scene.addItem(new Carpet());
        scene.addItem(new BottleOfRome());
        scene.addItem(new GoatInk());
        scene.addItem(new Rice((int) (Math.random()*50)+50));
        scene.addItem(new Gun());
        scene.addItem(new Clothing());
        scene.addItem(new Goat());
        scene.addItem(new Chest());
        scene.addItem(new Overcoat());
        String sentence4 = scene.start();

        scene = new SceneYearsOfReign();
        scene.addPerson(me);
        String sentence5 = scene.start();

        scene = new SceneAboutIsland();
        scene.addPlace(island);
        scene.addPlace(new Ridge());
        scene.addPlace(sea);
        scene.addPerson(me);
        scene.addItem(new Shallow());
        String sentence6 = scene.start();

        story.tell(new String[]{sentence1, sentence2, sentence3, sentence4, sentence5, sentence6});
    }
}
