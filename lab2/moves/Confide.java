package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class Confide extends StatusMove{
    public Confide(){
        super(Type.NORMAL, 0, 0);
    }

    @Override
    protected String describe (){
        return "Использует Confide";
    }

    @Override
    protected void applyOppEffects(Pokemon p){
        Effect effect = new Effect();
        effect.stat(Stat.SPECIAL_ATTACK, -1);
        p.addEffect(effect);
    }
}
