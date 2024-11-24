package pokemons;
import moves.TakeDown;
import ru.ifmo.se.pokemon.Type;

public class Chimecho extends Chingling{
        public Chimecho (String name, int level){
        super(name, level);
        this.setType(Type.PSYCHIC);
        this.setStats(75, 50, 80, 95, 90, 65);
        this.addMove(new TakeDown());
    }
}
