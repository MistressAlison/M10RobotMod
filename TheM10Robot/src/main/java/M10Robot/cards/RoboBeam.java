package M10Robot.cards;

import M10Robot.M10RobotMod;
import M10Robot.cards.abstractCards.AbstractDynamicCard;
import M10Robot.cards.abstractCards.AbstractReloadableCard;
import M10Robot.characters.M10Robot;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

import static M10Robot.M10RobotMod.makeCardPath;

public class RoboBeam extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = M10RobotMod.makeID(RoboBeam.class.getSimpleName());
    public static final String IMG = makeCardPath("RoboBeam.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = M10Robot.Enums.GREEN_SPRING_CARD_COLOR;

    private static final int COST = 1;
    private static final int DAMAGE = 3;
    private static final int UPGRADE_PLUS_DMG = 1;

    // /STAT DECLARATION/

    public RoboBeam() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        isMultiDamage = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0 ; i < GameActionManager.turn ; i++) {
            this.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractMonster mo = AbstractDungeon.getRandomMonster();
                    if (mo != null) {
                        this.addToTop(new DamageAction(mo, new DamageInfo(p, multiDamage[AbstractDungeon.getMonsters().monsters.indexOf(mo)], damageTypeForTurn), AbstractGameAction.AttackEffect.NONE, true));
                        this.addToTop(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
                        this.addToTop(new VFXAction(new SmallLaserEffect(mo.hb.cX, mo.hb.cY, p.hb.cX, p.hb.cY), 0.0F));
                    }
                    this.isDone = true;
                }
            });
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
