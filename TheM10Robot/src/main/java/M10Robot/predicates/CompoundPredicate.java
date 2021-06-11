package M10Robot.predicates;

import M10Robot.cards.interfaces.SwappableCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

public class CompoundPredicate {
    Predicate<AbstractCard> p;

    public CompoundPredicate(Predicate<AbstractCard> p) {
        this.p = p;
    }

    public boolean testPredicatesOnSwappable(AbstractCard c) {
        boolean b = p.test(c);
        if (c instanceof SwappableCard && c.cardsToPreview != null) {
            b |= p.test(c.cardsToPreview);
        }
        return b;
    }
}
