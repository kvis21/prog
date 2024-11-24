package pokemons;
import moves.PlayNice;
import ru.ifmo.se.pokemon.Type;

public class Steenee extends Bounsweet{
    public Steenee (String name, int level){
        super(name, level);
        this.setType(Type.GRASS);
        this.setStats(52, 40, 48, 40, 48, 62);
        this.addMove(new PlayNice());
    }
}
