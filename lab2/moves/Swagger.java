package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class Swagger extends StatusMove{
    public Swagger(){
        super(Type.NORMAL, 0, 0.85);
    }

    @Override
    protected String describe(){
        return "Использует Swagger";
    }

    @Override
    protected void applyOppEffects(Pokemon p){
        Effect effect = new Effect();
        effect = effect.stat(Stat.EVASION, +2);
        p.addEffect(effect);
        p.confuse();
    }
}
