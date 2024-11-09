package Pokemons;
import Moves.Confide;
import Moves.Facade;
import Moves.Growl;
import Moves.TakeDown;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Chimecho extends Pokemon{
        public Chimecho (String name, int level){
        super(name, level);
        this.setType(Type.PSYCHIC);
        this.setStats(75, 50, 80, 95, 90, 65);
        this.addMove(new Facade());
        this.addMove(new Confide());
        this.addMove(new Growl());
        this.addMove(new TakeDown());
    }
}
