package pokemons;
import moves.EnergyBall;
import ru.ifmo.se.pokemon.Type;

public class Tsareena extends Steenee{
    public Tsareena(String name, int level){
        super(name, level);
        this.setType(Type.GRASS);
        this.setStats(72, 120, 98, 50, 98, 72);
        this.addMove(new EnergyBall());
    }
}
