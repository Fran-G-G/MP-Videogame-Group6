import java.util.Random;

/**
 * Werewolf character.
 */
public class Werewolf extends AbstractCharacter {

    private int rage; // Range: 0 - 3
    private boolean transformed; // Human = false, Wolf = true
    private double height;
    private double weight;
    private final double heightTransformationIncrement;
    private final double weightTransformationIncrement;

    public Werewolf(String name, int health, int power, double height, double weight) {
        super(name, health, power);
        this.rage = 0;
        this.transformed = false;
        this.height = Math.round(height * 100.0) / 100.0;
        this.weight = Math.round(weight * 100.0) / 100.0;

        Random random = new Random();
        double factor = random.nextDouble() * 2.0; // Range [0.0, 2.0)

        // Calculate transformation increments directly without string parsing
        this.heightTransformationIncrement = 0.5 + 0.5 * (factor / 2.0);
        this.weightTransformationIncrement = 90.0 + 10.0 * factor;
    }

    public void transform() {
        transformed = !transformed;

        if (transformed) {
            height += heightTransformationIncrement;
            weight += weightTransformationIncrement;
        } else {
            height -= heightTransformationIncrement;
            weight -= weightTransformationIncrement;
        }
    }

    /**
     * Increases rage by 1 (max 3) when the werewolf takes damage.
     */
    public void increaseRageOnDamage() {
        if (rage < 3) {
            rage++;
        }
    }

    /**
     * Checks if the werewolf has enough rage to use its gift.
     */
    public boolean canUseGift() {
        if (skill instanceof Gift g) {
            return rage >= g.getRageCost();
        }
        return false;
    }

    public int getRage() {
        return rage;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public int getAttackPotential(boolean useSkill) {
        int potential = power;

        if (useSkill && skill instanceof Gift g) {
            if (rage >= g.getRageCost()) {
                potential += g.getAttackValue();
            }
        }

        for (Weapon w : activeWeapons) {
            potential += w.getAttackModifier();
        }
        if (activeArmour != null) {
            potential += activeArmour.getAttackModifier();
        }

        potential += rage; // current rage bonus

        potential += getStrengthsTotalModifier();
        potential -= getWeaknessesTotalModifier();

        return Math.max(potential, 0);
    }

    @Override
    public int getDefensePotential(boolean useSkill) {
        int potential = 0;

        if (useSkill && skill instanceof Gift g) {
            if (rage >= g.getRageCost()) {
                potential += g.getDefense();
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