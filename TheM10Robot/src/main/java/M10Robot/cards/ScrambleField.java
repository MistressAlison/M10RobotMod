package M10Robot.cards;

import M10Robot.M10RobotMod;
import M10Robot.cards.abstractCards.AbstractDynamicCard;
import M10Robot.characters.M10Robot;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.IntimidateEffect;

import static M10Robot.M10RobotMod.makeCardPath;

public class ScrambleField extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = M10RobotMod.makeID(ScrambleField.class.getSimpleName());
    public static final String IMG = makeCardPath("ScrambledDeltaField.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = M10Robot.Enums.GREEN_SPRING_CARD_COLOR;

    private static final int COST = -1;
    private static final int EFFECT = 0;
    private static final int UPGRADE_PLUS_EFFECT = 1;
    private static final int STR_DOWN = 1;
    // /STAT DECLARATION/


    public ScrambleField() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = EFFECT;
        secondMagicNumber = baseSecondMagicNumber = STR_DOWN;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = this.energyOnUse;

        if (p.hasRelic("Chemical X")) {
            effect += ChemicalX.BOOST;
            p.getRelic("Chemical X").flash();
        }

        effect += magicNumber;

        if (effect > 0) {
            this.addToBot(new SFXAction("ORB_PLASMA_CHANNEL"));
            this.addToBot(new VFXAction(p, new IntimidateEffect(p.hb.cX, p.hb.cY), 0.7F));
            this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, effect)));
            for (AbstractMonster aM : AbstractDungeon.getMonsters().monsters) {
                if (!aM.isDeadOrEscaped()) {
                    this.addToBot(new ApplyPowerAction(aM, p, new StrengthPower(aM, -effect), -effect));
                }
            }
        }

        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }

    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_EFFECT);
            initializeDescription();
        }
    }
}
