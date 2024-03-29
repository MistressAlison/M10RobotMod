package M10Robot.cards;

import M10Robot.M10RobotMod;
import M10Robot.actions.MultichannelAction;
import M10Robot.cards.abstractCards.AbstractDynamicCard;
import M10Robot.characters.M10Robot;
import M10Robot.orbs.SearchlightOrb;
import M10Robot.powers.RecoilPower;
import M10Robot.powers.SpikesPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static M10Robot.M10RobotMod.makeCardPath;

public class SpikeBall extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = M10RobotMod.makeID(SpikeBall.class.getSimpleName());
    public static final String IMG = makeCardPath("SpikeBall.png");
    // Setting the image as as easy as can possibly be now. You just need to provide the image name

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = M10Robot.Enums.GREEN_SPRING_CARD_COLOR;

    private static final int COST = 2;
    private static final int AMOUNT = 12;
    private static final int UPGRADE_PLUS_AMOUNT = 4;
    private static final int SPIKES = 5;
    private static final int UPGRADE_PLUS_SPIKES = 2;

    // /STAT DECLARATION/

    public SpikeBall() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = AMOUNT;
        magicNumber = baseMagicNumber = SPIKES;
        //showEvokeValue = true;
        //CardModifierManager.addModifier(this, new HeavyModifier());
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, block));
        this.addToBot(new ApplyPowerAction(p, p, new SpikesPower(p, magicNumber)));
        //this.addToBot(new MultichannelAction(new SearchlightOrb(), magicNumber));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_AMOUNT);
            upgradeMagicNumber(UPGRADE_PLUS_SPIKES);
            initializeDescription();
        }
    }
}
