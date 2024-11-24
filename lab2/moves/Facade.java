package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Status;
import ru.ifmo.se.pokemon.Type;

public class Facade extends PhysicalMove{
    public Facade(){
        super(Type.NORMAL, 70, 1.0);
    }

    @Override
    protected String describe (){
        return "Использует Facade";
    }

    @Override
    protected void applyOppEffects(Pokemon p){
        Status pokemonStatus= p.getCondition();
        if (pokemonStatus == Status.BURN || pokemonStatus == Status.PARALYZE || pokemonStatus == Status.POISON){
            this.power*=2;
        }
    }
}
