/**
 * Hunter character.
 */
public class Hunter extends AbstractCharacter {

    private int willpower; // Range: 0 - 3

    public Hunter(String name, int health, int power) {
        super(name, health, power);
        this.willpower = 3;
    }

    /**
     * Decreases willpower by 1 (min 0) when the hunter takes damage.
     */
    public void decreaseWillpowerOnDamage() {
        if (willpower > 0) {
            willpower--;
        }
    }

    public int getWill() {
        return willpower;
    }

    @Override
    public int getAttackPotential(boolean useSkill) {
        int potential = power;

        if (useSkill && skill != null) {
            potential += skill.getAttackValue();
        }

        for (Weapon w : activeWeapons) {
            potential += w.getAttackModifier();
        }
        if (activeArmour != null) {
            potential += activeArmour.getAttackModifier();
        }

        potential += willpower; // current willpower bonus

        potential += getStrengthsTotalModifier();
        potential -= getWeaknessesTotalModifier();

        return Math.max(potential, 0);
    }

    @Override
    public int getDefensePotential(boolean useSkill) {
        int potential = 0;

        if (useSkill && skill != null) {
            potential += skill.getDefense();
        }

        if (activeArmour != null) {
            potential += activeArmour.getDefenseModifier();
        }
        for (Weapon w : activeWeapons) {
            potential += w.getDefenseModifier();
        }

        potential += getStrengthsTotalModifier();
        potential -= getWeaknessesTotalModifier();

        return Math.max(potential, 0);
    }
}