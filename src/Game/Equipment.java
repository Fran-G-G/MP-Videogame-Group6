package Game;

import java.io.Serializable;

/**
 * Base class for all equipment.
 */
public abstract class Equipment implements Serializable {

    protected String name;
    protected int attackModifier; // 0-3
    protected int defenseModifier; // 0-3

    public Equipment(String name, int attackModifier, int defenseModifier) {
        this.name = name;
        this.attackModifier = attackModifier;
        this.defenseModifier = defenseModifier;
    }

    public String getName() {
        return name;
    }

    public int getAttackModifier() {
        return attackModifier;
    }

    public int getDefenseModifier() {
        return defenseModifier;
    }

}