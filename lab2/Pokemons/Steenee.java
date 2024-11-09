package Pokemons;
import Moves.PlayNice;
import Moves.Swagger;
import Moves.SweetScent;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Steenee extends Pokemon{
    public Steenee (String name, int level){
        super(name, level);
        this.setType(Type.GRASS);
        this.setStats(52, 40, 48, 40, 48, 62);
        this.addMove(new Swagger());
        this.addMove(new SweetScent());
        this.addMove(new PlayNice());
    }
}
