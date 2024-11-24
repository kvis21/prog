package pokemons;

import moves.Confide;
import moves.Facade;
import moves.Growl;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Chingling extends Pokemon{
    public Chingling (String name, int level){
        super(name, level);
        this.setType(Type.PSYCHIC);
        this.setStats(45, 30, 50, 65, 50, 45);
        this.addMove(new Facade());
        this.addMove(new Confide());
        this.addMove(new Growl());
    }
}
