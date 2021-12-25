package M10Robot.actions;

import M10Robot.cardModifiers.OverclockModifier;
import M10Robot.cards.abstractCards.AbstractSwappableCard;
import M10Robot.util.OverclockUtil;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class OverclockCardAction extends AbstractGameAction {
    private AbstractCard card;
    private boolean allCards;

    public OverclockCardAction(AbstractCard card, int rawPercent) {
        this.card = card;
        this.amount = rawPercent;
    }

    public OverclockCardAction(boolean allCards, int rawPercent) {
        this.allCards = allCards;
        this.amount = rawPercent;
    }

    @Override
    public void update() {
        if (allCards) {
            CardCrawlGame.sound.play("GHOST_ORB_IGNITE_1", 0.1F);
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (OverclockUtil.canOverclock(c)) {
                    CardModifierManager.addModifier(c, new OverclockModifier(amount));
                    c.superFlash();
                    if (c instanceof AbstractSwappableCard) {
                        CardModifierManager.addModifier(c.cardsToPreview, new OverclockModifier(amount));
                    }
                }
            }
        } else {
            if (card == null) {
                CardGroup validCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (OverclockUtil.canOverclock(c)) {
                        validCards.addToTop(c);
                    }
                }
                if (validCards.isEmpty()) {
                    this.isDone = true;
                    return;
                }
                card = validCards.getRandomCard(true);
            }
            CardCrawlGame.sound.play("GHOST_ORB_IGNITE_1", 0.1F);
            CardModifierManager.addModifier(card, new OverclockModifier(amount));
            card.superFlash();
            if (card instanceof AbstractSwappableCard) {
                CardModifierManager.addModifier(card.cardsToPreview, new OverclockModifier(amount));
            }
        }

        this.isDone = true;
    }
}
