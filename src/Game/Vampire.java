package Game;

import java.io.Serializable;

/**
 * Vampire character.
 */
public class Vampire extends AbstractCharacter implements Serializable {

    private int blood; // Range: 0 - 10
    private int age;

    public Vampire(String name, int health, int power, int age) {
        super(name, health, power);
        this.age = age;
        this.blood = 10;
    }

    /**
     * Attempts to use the vampire's discipline.
     * @return true if the discipline was used (blood cost paid), false otherwise.
     */
    public boolean useDisciplineInCombat() {
        if (!(skill instanceof Discipline d)) {
            return false;
        }
        if (blood >= d.getBloodCost()) {
            blood -= d.getBloodCost();
            return true;
        }
        return false;
    }

    /**
     * Recovers 4 blood points after a successful attack (max 10).
     */
    public void recoverBlood() {
        blood = Math.min(10, blood + 4);
    }

    public int getBloodPoints() {
        return blood;
    }

    public int getAge() {
        return age;
    }

    @Override
    public int getAttackPotential(boolean useSkill) {
        int potential = power;

        if (useSkill && skill instanceof Discipline d) {
            if (blood >= d.getBloodCost()) {
                potential += d.getAttackValue();
            }
        }

        for (Weapon w : activeWeapons) {
            potential += w.getAttackModifier();
        }
        if (activeArmour != null) {
            potential += activeArmour.getAttackModifier();
        }

        // Blood bonus
        if (blood >= 5) {
            potential += 2;
        }

        potential += getStrengthsTotalModifier();
        potential -= getWeaknessesTotalModifier();

        return Math.max(potential, 0);
    }

    @Override
    public int getDefensePotential(boolean useSkill) {
        int potential = 0;

        if (useSkill && skill instanceof Discipline d) {
            if (blood >= d.getBloodCost()) {
                potential += d.getDefense();
            }
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