package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class SweetScent extends StatusMove{
    public SweetScent (){
        super(Type.NORMAL, 0, 1);
    }

    @Override
    protected String describe(){
        return "Использует SweetScent";
    }

    @Override
    protected void applyOppEffects(Pokemon p){
        Effect effect = new Effect();
        effect = effect.stat(Stat.EVASION, -1);
        p.addEffect(effect);
    }
}
