package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class Smokescreen extends StatusMove{
     public Smokescreen(){
        super(Type.NORMAL, 0, 1.0);
    }

    @Override
    protected String describe (){
        return "Использует Smokescreen";
    }

    @Override
    protected void applyOppEffects(Pokemon p){
        Effect effect = new Effect();
        effect.stat(Stat.ACCURACY, -1);
        p.addEffect(effect);
    }
}
