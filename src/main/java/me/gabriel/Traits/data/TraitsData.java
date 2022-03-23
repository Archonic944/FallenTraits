package me.gabriel.Traits.data;


import me.zach.DesertMC.Utils.Config.ConfigUtils;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TraitsData {
    public static final float SPEED_TICK = 0.2f / 100;
    public static final float HEALTH_TICK = 20f / 100;
    public static final int BONUS_FACTOR = 2;
    @BsonProperty("defense")
    int defenseLevel = 0;
    @BsonProperty("attack")
    int attackLevel = 0;
    @BsonProperty("speed")
    int speedLevel = 0;
    @BsonProperty("health")
    int healthLevel = 0;
    @BsonProperty("trait_tokens")
    int traitTokens = 0;

    public int getTraitTokens(){
        return traitTokens;
    }

    public void setTraitTokens(int traitTokens){
        this.traitTokens = traitTokens;
    }

    public int getDefenseLevel(){
        return defenseLevel;
    }

    public void setDefenseLevel(int defenseLevel){
        this.defenseLevel = defenseLevel;
    }

    public int getAttackLevel(){
        return attackLevel;
    }

    public void setAttackLevel(int attackLevel){
        this.attackLevel = attackLevel;
    }

    public int getSpeedLevel(){
        return speedLevel;
    }

    public void setSpeedLevel(int speedLevel){
        this.speedLevel = speedLevel;
    }

    public int getHealthLevel(){
        return healthLevel;
    }

    public void setHealthLevel(int healthLevel){
        this.healthLevel = healthLevel;
    }

    public int levelGet(String trait){
        if(trait.equals("speed")) return getSpeedLevel();
        else if(trait.equals("attack")) return getAttackLevel();
        else if(trait.equals("defense")) return getDefenseLevel();
        else if(trait.equals("health")) return getHealthLevel();
        else throw new IllegalArgumentException("Cannot get level of trait '" + trait + "'");
    }

    public void levelSet(String trait, int level){
        if(trait.equals("speed")) setSpeedLevel(level);
        else if(trait.equals("attack")) setAttackLevel(level);
        else if(trait.equals("defense")) setDefenseLevel(level);
        else if(trait.equals("health")) setHealthLevel(level);
        else throw new IllegalArgumentException("Cannot set level of trait '" + trait + "'");
    }

    public boolean canUpgrade(String trait, int gems){
        int level = levelGet(trait);
        return gems >= gemsToNext(level) && traitTokens >= ttsToNext(level);
    }

    public static int gemsToNext(int level){
        return level * 250 + 250;
    }

    public static int ttsToNext(int level){
        if(level >= 16) return 3;
        else if(level >= 4) return 2;
        else return 1;
    }

    public static int bonus(int level){
        return level * BONUS_FACTOR;
    }

    public static TraitsData get(Player player){
        return get(player.getUniqueId());
    }

    public static TraitsData get(UUID uuid){
        return ConfigUtils.getData(uuid).getTraitsData();
    }
}
