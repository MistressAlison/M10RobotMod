package M10Robot.actions;

import M10Robot.cards.abstractCards.AbstractSwappableCard;
import M10Robot.patches.OverclockPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OverclockCardAction extends AbstractGameAction {
    private AbstractCard card;
    private boolean allCards;

    public OverclockCardAction(AbstractCard card, int amount) {
        this.card = card;
        this.amount = amount;
    }

    public OverclockCardAction(boolean allCards, int amount) {
        this.allCards = allCards;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (allCards) {
            CardCrawlGame.sound.play("GHOST_ORB_IGNITE_1", 0.1F);
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (OverclockPatches.canOverclock(c)) {
                    OverclockPatches.overclock(c, amount);
                    c.superFlash();
                    if (c instanceof AbstractSwappableCard) {
                        OverclockPatches.overclock(c.cardsToPreview, amount);
                    }
                }
            }
        } else {
            if (card == null) {
                CardGroup validCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (OverclockPatches.canOverclock(c)) {
                        validCards.addToTop(c);
                    }
                }
                if (validCards.isEmpty()) {
                    this.isDone = true;
                    return;
                }
                card = validCards.getRandomCard(true);
            }
            if (card != null) {
                CardCrawlGame.sound.play("GHOST_ORB_IGNITE_1", 0.1F);
                OverclockPatches.overclock(card, amount);
                //CardModifierManager.addModifier(card, new OverclockModifier(amount));
                card.superFlash();
                if (card instanceof AbstractSwappableCard) {
                    OverclockPatches.overclock(card.cardsToPreview, amount);
                    //CardModifierManager.addModifier(card.cardsToPreview, new OverclockModifier(amount));
                }
            }
        }
        AbstractDungeon.player.hand.refreshHandLayout();
        this.isDone = true;
    }
}
