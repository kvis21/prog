package Pokemons;
import Moves.EnergyBall;
import Moves.PlayNice;
import Moves.Swagger;
import Moves.SweetScent;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Tsareena extends Pokemon{
    public Tsareena(String name, int level){
        super(name, level);
        this.setType(Type.GRASS);
        this.setStats(72, 120, 98, 50, 98, 72);
        this.addMove(new Swagger());
        this.addMove(new SweetScent());
        this.addMove(new PlayNice());
        this.addMove(new EnergyBall());
    }
}
