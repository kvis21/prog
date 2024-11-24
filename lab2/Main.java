import ru.ifmo.se.pokemon.*;
import pokemons.*;

public class Main {
    public static void main(String []args){
        Battle b = new Battle();
        
        Pokemon pokemon1 = new Spiritomb("Пушок", 1);
        Pokemon pokemon2 = new Chingling("Лео", 1);
        Pokemon pokemon3 = new Chimecho("ледик", 1);
        Pokemon pokemon4 = new Bounsweet("зубик", 1);
        Pokemon pokemon5 = new Steenee("грозик", 1);
        Pokemon pokemon6 = new Tsareena("солнышко", 1);

        b.addAlly(pokemon1);
        b.addAlly(pokemon2);
        b.addAlly(pokemon3);
        b.addFoe(pokemon4);
        b.addFoe(pokemon5);
        b.addFoe(pokemon6);
        b.go();
    }
}
