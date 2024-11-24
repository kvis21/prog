package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class EnergyBall extends SpecialMove{
    public EnergyBall(){
        super(Type.GRASS, 90, 1.0);
    }

    @Override
    protected String describe(){
        return "Использует Energy Ball";
    }

    @Override
    protected void applyOppEffects(Pokemon p){
        Effect effect = new Effect();
        effect.chance(0.1);
        effect = effect.stat(Stat.SPECIAL_DEFENSE, -1);
        p.addEffect(effect);
    }
}
