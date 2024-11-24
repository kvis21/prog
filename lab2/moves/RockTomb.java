package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class RockTomb extends PhysicalMove{
    public RockTomb(){
        super(Type.ROCK, 60, 0.95);
    }

    @Override
    protected String describe (){
        return "Использует Rock Tomb";
    }

    @Override
    protected void applyOppEffects(Pokemon p){
        Effect effect = new Effect();
        effect.chance(1.0);
        effect.stat(Stat.SPEED, -1);
        p.addEffect(effect);
    }
}
