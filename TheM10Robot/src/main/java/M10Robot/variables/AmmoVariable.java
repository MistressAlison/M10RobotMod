package M10Robot.variables;

import M10Robot.cards.abstractCards.AbstractModdedCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static M10Robot.M10RobotMod.makeID;

public class AmmoVariable extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.
    //public static boolean invertColor = false;

    @Override
    public String key() {
        return makeID("AmmoCount");
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractModdedCard) card).isAmmoCountModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractModdedCard) card).ammoCount;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractModdedCard) card).baseAmmoCount;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractModdedCard) card).upgradedAmmoCount;
    }

}