package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class Snarl extends SpecialMove{
    public Snarl(){
        super(Type.DARK, 55, 0.95);
    }

    @Override
    protected String describe (){
        return "Использует Snarl";
    }

    @Override
    protected void applyOppEffects(Pokemon p){
        Effect effect = new Effect();
        effect.chance(1.0);
        effect.stat(Stat.SPECIAL_ATTACK, -1);
        p.addEffect(effect);
    }
}
