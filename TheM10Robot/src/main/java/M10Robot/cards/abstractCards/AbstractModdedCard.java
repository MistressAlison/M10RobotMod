package M10Robot.cards.abstractCards;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;

public abstract class AbstractModdedCard extends CustomCard {

    // Custom Abstract Cards can be a bit confusing. While this is a simple base for simply adding a second magic number,
    // if you're new to modding I suggest you skip this file until you know what unique things that aren't provided
    // by default, that you need in your own cards.

    // In this example, we use a custom Abstract Card in order to define a new magic number. From here on out, we can
    // simply use that in our cards, so long as we put "extends AbstractDynamicCard" instead of "extends CustomCard" at the start.
    // In simple terms, it's for things that we don't want to define again and again in every single card we make.

    public int secondMagicNumber = -1;        // Just like magic number, or any number for that matter, we want our regular, modifiable stat
    public int baseSecondMagicNumber = -1;    // And our base stat - the number in it's base state. It will reset to that by default.
    public boolean upgradedSecondMagicNumber; // A boolean to check whether the number has been upgraded or not.
    public boolean isSecondMagicNumberModified; // A boolean to check whether the number has been modified or not, for coloring purposes. (red/green)

    public int info = -1;
    public int baseInfo = -1;
    public boolean upgradedInfo;
    public boolean isInfoModified;

    public int invertedNumber = -1;
    public int baseInvertedNumber = -1;
    public boolean upgradedInvertedNumber;
    public boolean isInvertedNumberModified;

    public int currentAmmo = -1;
    public int maxAmmo = -1;
    public int baseMaxAmmo = -1;
    public boolean upgradedMaxAmmo;
    public boolean isCurrentAmmoModified;
    public boolean isMaxAmmoModified;

    public CardStrings cardStrings;
    public String DESCRIPTION; //The main description of the card
    public String UPGRADE_DESCRIPTION; //The upgrade description of the card, if applicable
    public String[] EXTENDED_DESCRIPTION; //The Norma Effects of the card
    public String NAME; //The base name of the card

    public AbstractModdedCard(final String id,
                              final String name,
                              final String img,
                              final int cost,
                              final String rawDescription,
                              final CardType type,
                              final CardColor color,
                              final CardRarity rarity,
                              final CardTarget target) {

        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        // Set all the things to their default values.
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isSecondMagicNumberModified = false;
        isInfoModified = false;
        isInvertedNumberModified = false;
        isCurrentAmmoModified = false;
        isMaxAmmoModified = false;

        CommonKeywordIconsField.useIcons.set(this, true);

        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        NAME = cardStrings.NAME;

        initializeDescription();
    }

    public void displayUpgrades() { // Display the upgrade - when you click a card to upgrade it
        super.displayUpgrades();
        if (upgradedSecondMagicNumber) { // If we set upgradedDefaultSecondMagicNumber = true in our card.
            secondMagicNumber = baseSecondMagicNumber; // Show how the number changes, as out of combat, the base number of a card is shown.
            isSecondMagicNumberModified = true; // Modified = true, color it green to highlight that the number is being changed.
        }
        if (upgradedInfo) { // If we set upgradedDefaultSecondMagicNumber = true in our card.
            info = baseInfo; // Show how the number changes, as out of combat, the base number of a card is shown.
            isInfoModified = true; // Modified = true, color it green to highlight that the number is being changed.
        }
        if (upgradedInvertedNumber) { // If we set upgradedDefaultSecondMagicNumber = true in our card.
            invertedNumber = baseInvertedNumber; // Show how the number changes, as out of combat, the base number of a card is shown.
            isInvertedNumberModified = true; // Modified = true, color it green to highlight that the number is being changed.
        }
        if (upgradedMaxAmmo) { // If we set upgradedDefaultSecondMagicNumber = true in our card.
            maxAmmo = baseMaxAmmo; // Show how the number changes, as out of combat, the base number of a card is shown.
            isMaxAmmoModified = true; // Modified = true, color it green to highlight that the number is being changed.
            isCurrentAmmoModified = true;
        }
    }

    public void upgradeSecondMagicNumber(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        baseSecondMagicNumber += amount; // Upgrade the number by the amount you provide in your card.
        secondMagicNumber = baseSecondMagicNumber; // Set the number to be equal to the base value.
        upgradedSecondMagicNumber = true; // Upgraded = true - which does what the above method does.
    }

    public void upgradeInfo(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        baseInfo += amount; // Upgrade the number by the amount you provide in your card.
        info = baseInfo; // Set the number to be equal to the base value.
        upgradedInfo = true; // Upgraded = true - which does what the above method does.
    }

    public void upgradeInvertedNumber(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        baseInvertedNumber += amount; // Upgrade the number by the amount you provide in your card.
        invertedNumber = baseInvertedNumber; // Set the number to be equal to the base value.
        upgradedInvertedNumber = true; // Upgraded = true - which does what the above method does.
    }

    public void upgradeMaxAmmo(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        baseMaxAmmo += amount;
        maxAmmo = baseMaxAmmo; // Upgrade the number by the amount you provide in your card.
        currentAmmo += amount; // Increase the current ammo by the same amount
        upgradedMaxAmmo = true; // Upgraded = true - which does what the above method does.
    }

    @Override
    public void resetAttributes() {
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.isSecondMagicNumberModified = false;
        this.info = this.baseInfo;
        this.isInfoModified = false;
        this.invertedNumber = this.baseInvertedNumber;
        this.isInvertedNumberModified = false;
        this.maxAmmo = this.baseMaxAmmo;
        this.isMaxAmmoModified = false;
        //Don't reset current ammo
        super.resetAttributes();
    }

    public void setDisplayBannerRarity(CardRarity rarity) {
        switch(rarity) {
            case BASIC:
            case CURSE:
            case SPECIAL:
            case COMMON:
                this.bannerSmallRegion = ImageMaster.CARD_BANNER_COMMON;
                this.bannerLargeRegion = ImageMaster.CARD_BANNER_COMMON_L;
                /*switch(this.type) {
                    case ATTACK:
                        this.frameSmallRegion = ImageMaster.CARD_FRAME_ATTACK_COMMON;
                        this.frameLargeRegion = ImageMaster.CARD_FRAME_ATTACK_COMMON_L;
                        break;
                    case POWER:
                        this.frameSmallRegion = ImageMaster.CARD_FRAME_POWER_COMMON;
                        this.frameLargeRegion = ImageMaster.CARD_FRAME_POWER_COMMON_L;
                        break;
                    default:
                        this.frameSmallRegion = ImageMaster.CARD_FRAME_SKILL_COMMON;
                        this.frameLargeRegion = ImageMaster.CARD_FRAME_SKILL_COMMON_L;
                }*/

                /*this.frameMiddleRegion = ImageMaster.CARD_COMMON_FRAME_MID;
                this.frameLeftRegion = ImageMaster.CARD_COMMON_FRAME_LEFT;
                this.frameRightRegion = ImageMaster.CARD_COMMON_FRAME_RIGHT;
                this.frameMiddleLargeRegion = ImageMaster.CARD_COMMON_FRAME_MID_L;
                this.frameLeftLargeRegion = ImageMaster.CARD_COMMON_FRAME_LEFT_L;
                this.frameRightLargeRegion = ImageMaster.CARD_COMMON_FRAME_RIGHT_L;*/
                break;
            case UNCOMMON:
                this.bannerSmallRegion = ImageMaster.CARD_BANNER_UNCOMMON;
                this.bannerLargeRegion = ImageMaster.CARD_BANNER_UNCOMMON_L;
                /*switch(this.type) {
                    case ATTACK:
                        this.frameSmallRegion = ImageMaster.CARD_FRAME_ATTACK_UNCOMMON;
                        this.frameLargeRegion = ImageMaster.CARD_FRAME_ATTACK_UNCOMMON_L;
                        break;
                    case POWER:
                        this.frameSmallRegion = ImageMaster.CARD_FRAME_POWER_UNCOMMON;
                        this.frameLargeRegion = ImageMaster.CARD_FRAME_POWER_UNCOMMON_L;
                        break;
                    default:
                        this.frameSmallRegion = ImageMaster.CARD_FRAME_SKILL_UNCOMMON;
                        this.frameLargeRegion = ImageMaster.CARD_FRAME_SKILL_UNCOMMON_L;
                }*/

                /*this.frameMiddleRegion = ImageMaster.CARD_UNCOMMON_FRAME_MID;
                this.frameLeftRegion = ImageMaster.CARD_UNCOMMON_FRAME_LEFT;
                this.frameRightRegion = ImageMaster.CARD_UNCOMMON_FRAME_RIGHT;
                this.frameMiddleLargeRegion = ImageMaster.CARD_UNCOMMON_FRAME_MID_L;
                this.frameLeftLargeRegion = ImageMaster.CARD_UNCOMMON_FRAME_LEFT_L;
                this.frameRightLargeRegion = ImageMaster.CARD_UNCOMMON_FRAME_RIGHT_L;*/
                break;
            case RARE:
                this.bannerSmallRegion = ImageMaster.CARD_BANNER_RARE;
                this.bannerLargeRegion = ImageMaster.CARD_BANNER_RARE_L;
                /*switch(this.type) {
                    case ATTACK:
                        this.frameSmallRegion = ImageMaster.CARD_FRAME_ATTACK_RARE;
                        this.frameLargeRegion = ImageMaster.CARD_FRAME_ATTACK_RARE_L;
                        break;
                    case POWER:
                        this.frameSmallRegion = ImageMaster.CARD_FRAME_POWER_RARE;
                        this.frameLargeRegion = ImageMaster.CARD_FRAME_POWER_RARE_L;
                        break;
                    default:
                        this.frameSmallRegion = ImageMaster.CARD_FRAME_SKILL_RARE;
                        this.frameLargeRegion = ImageMaster.CARD_FRAME_SKILL_RARE_L;
                }*/

                /*this.frameMiddleRegion = ImageMaster.CARD_RARE_FRAME_MID;
                this.frameLeftRegion = ImageMaster.CARD_RARE_FRAME_LEFT;
                this.frameRightRegion = ImageMaster.CARD_RARE_FRAME_RIGHT;
                this.frameMiddleLargeRegion = ImageMaster.CARD_RARE_FRAME_MID_L;
                this.frameLeftLargeRegion = ImageMaster.CARD_RARE_FRAME_LEFT_L;
                this.frameRightLargeRegion = ImageMaster.CARD_RARE_FRAME_RIGHT_L;*/
                break;
            default:
                customBannerHook(rarity);
        }
    }

    public void customBannerHook(CardRarity rarity) {}
}