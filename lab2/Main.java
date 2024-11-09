import ru.ifmo.se.pokemon.*;
import Pokemons.*;

public class Main {
    public static void main(String []args){
        Battle b = new Battle();
        
        Pokemon pokemon1 = new Spiritomb("Синий1", 1);
        Pokemon pokemon2 = new Chingling("Синий2", 1);
        Pokemon pokemon3 = new Chimecho("Синий3", 1);
        Pokemon pokemon4 = new Bounsweet("Красный1", 1);
        Pokemon pokemon5 = new Steenee("Красный2", 1);
        Pokemon pokemon6 = new Tsareena("Красный3", 1);

        b.addAlly(pokemon1);
        b.addAlly(pokemon2);
        b.addAlly(pokemon3);
        b.addFoe(pokemon4);
        b.addFoe(pokemon5);
        b.addFoe(pokemon6);
        b.go();
    }
}
