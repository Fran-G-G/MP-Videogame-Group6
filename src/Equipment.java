/**
 * Base class for all equipment.
 */
public abstract class Equipment {

    protected String name;
    protected int attackModifier; // Puede ser 0
    protected int defenseModifier; // Puede ser 0

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

