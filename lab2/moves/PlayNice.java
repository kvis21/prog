package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class PlayNice extends StatusMove{
    public PlayNice(){
        super(Type.NORMAL, 0, 0.85);
    }

    @Override
    protected String describe(){
        return "Использует Play Nice";
    }

    @Override
    protected void applyOppEffects(Pokemon p){
        Effect effect = new Effect();
        effect = effect.stat(Stat.EVASION, -1);
        p.addEffect(effect);
    }
}
