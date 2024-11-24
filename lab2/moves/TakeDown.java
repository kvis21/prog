package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class TakeDown extends PhysicalMove{
    public TakeDown(){
        super(Type.NORMAL, 90, 0.85);
    }

    @Override
    protected String describe (){
        return "Использует TakeDown";
    }

    @Override
    protected void applyOppDamage(Pokemon p, double damage) {
        p.setMod(Stat.HP, (int) Math.round(damage/4));
    }
}
