package Pokemons;
import Moves.CalmMind;
import Moves.RockTomb;
import Moves.Smokescreen;
import Moves.Snarl;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Spiritomb extends Pokemon{
    public Spiritomb (String name, int level){
        super(name, level);
        this.setType(Type.DARK, Type.GHOST);
        this.setStats(50, 92, 108, 92, 108, 35);
        this.addMove(new RockTomb());
        this.addMove(new CalmMind());
        this.addMove(new Smokescreen());
        this.addMove(new Snarl());
    }
}
