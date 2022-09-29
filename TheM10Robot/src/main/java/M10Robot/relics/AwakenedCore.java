package M10Robot.relics;

import M10Robot.M10RobotMod;
import M10Robot.util.TextureLoader;
import M10Robot.util.interfaces.OverclockBeforePlayItem;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.HashMap;

import static M10Robot.M10RobotMod.makeRelicOutlinePath;
import static M10Robot.M10RobotMod.makeRelicPath;

public class AwakenedCore extends CustomRelic implements OverclockBeforePlayItem {

    // ID, images, text.
    public static final String ID = M10RobotMod.makeID("AwakenedCore");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Eyes2.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Eyes.png"));

    public static final int STACKS = 2;

    HashMap<String, Integer> stats = new HashMap<>();
    private final String ATTACKS_CLOCKED = DESCRIPTIONS[2];
    private final String SKILLS_CLOCKED = DESCRIPTIONS[3];
    private final String POWERS_CLOCKED = DESCRIPTIONS[4];
    private boolean triggeredThisTurn = false;

    public AwakenedCore() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
        resetStats();
    }

    public void atTurnStart() {
        this.triggeredThisTurn = false;
    }

    @Override
    public int overclockAmount(AbstractCard card) {
        if (!triggeredThisTurn) {
            return STACKS;
        }
        return 0;
    }

    @Override
    public void onOverclock(AbstractCard card) {
        if (!triggeredThisTurn) {
            flash();
            pulse = false;
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            triggeredThisTurn = true;
            switch (card.type) {
                case ATTACK:
                    stats.put(ATTACKS_CLOCKED, stats.get(ATTACKS_CLOCKED)+1);
                    break;
                case SKILL:
                    stats.put(SKILLS_CLOCKED, stats.get(SKILLS_CLOCKED)+1);
                    break;
                case POWER:
                    stats.put(POWERS_CLOCKED, stats.get(POWERS_CLOCKED)+1);
                    break;
            }
        }
    }

    @Override //Should replace default relic. Big thanks papa kio
    public void obtain() {
        //Grab the player
        AbstractPlayer p = AbstractDungeon.player;
        //If we have the starter relic...
        if (p.hasRelic(DualCore.ID)) {
            //Grab its data for relic stats if you want to carry the stats over to the boss relic
            DualCore r = (DualCore) p.getRelic(DualCore.ID);
            stats.put(ATTACKS_CLOCKED, r.getAttacks());
            stats.put(SKILLS_CLOCKED, r.getSkills());
            stats.put(POWERS_CLOCKED, r.getPowers());
            //Find it...
            for (int i = 0; i < p.relics.size(); ++i) {
                if (p.relics.get(i).relicId.equals(DualCore.ID)) {
                    //Replace it
                    instantObtain(p, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    //Only spawn if we have the starter relic
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(DualCore.ID);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + STACKS + DESCRIPTIONS[1];
    }

    public String getStatsDescription() {
        return ATTACKS_CLOCKED + stats.get(ATTACKS_CLOCKED) + SKILLS_CLOCKED + stats.get(SKILLS_CLOCKED) + POWERS_CLOCKED + stats.get(POWERS_CLOCKED);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        return getStatsDescription();
    }

    public void resetStats() {
        stats.put(ATTACKS_CLOCKED, 0);
        stats.put(SKILLS_CLOCKED, 0);
        stats.put(POWERS_CLOCKED, 0);
    }

    public int getAttacks() {
        return stats.get(ATTACKS_CLOCKED);
    }

    public int getSkills() {
        return stats.get(SKILLS_CLOCKED);
    }

    public int getPowers() {
        return stats.get(POWERS_CLOCKED);
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<Integer> statsToSave = new ArrayList<>();
        statsToSave.add(stats.get(ATTACKS_CLOCKED));
        statsToSave.add(stats.get(SKILLS_CLOCKED));
        statsToSave.add(stats.get(POWERS_CLOCKED));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(ATTACKS_CLOCKED, jsonArray.get(0).getAsInt());
            stats.put(SKILLS_CLOCKED, jsonArray.get(1).getAsInt());
            stats.put(POWERS_CLOCKED, jsonArray.get(2).getAsInt());
        } else {
            resetStats();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        // Relic Stats will always query the stats from the instance passed to BaseMod.addRelic()
        // Therefore, we make sure all copies share the same stats by copying the HashMap.
        AwakenedCore newRelic = new AwakenedCore();
        newRelic.stats = this.stats;
        return newRelic;
    }

}
