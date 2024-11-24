package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class CalmMind extends StatusMove{
    public CalmMind(){
        super(Type.PSYCHIC, 0, 0);
    }

    @Override
    protected String describe() {
        return "Использует CalmMind";
    }
    
    @Override
    protected void applySelfEffects(Pokemon p) {
        Effect effect = new Effect();
        effect = effect.stat(Stat.SPECIAL_ATTACK, +1);
        effect = effect.stat(Stat.SPECIAL_DEFENSE, +1);
        p.addEffect(effect);
    }
}
