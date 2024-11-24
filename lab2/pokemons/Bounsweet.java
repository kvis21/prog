package pokemons;
import moves.Swagger;
import moves.SweetScent;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Bounsweet extends Pokemon{
    public Bounsweet (String name, int level){
        super(name, level);
        this.setType(Type.GRASS);
        this.setStats(42, 30, 38, 30, 38, 32);
        this.addMove(new Swagger());
        this.addMove(new SweetScent());
    }
}
