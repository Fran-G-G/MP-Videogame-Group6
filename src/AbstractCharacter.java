import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all characters.
 */
public abstract class AbstractCharacter {

    protected String name;
    protected int health; // Range: 0 - 5
    protected int power;  // Range: 1 - 5
    protected int gold;

    protected SpecialSkill skill;

    protected List<Weapon> weapons;
    protected List<Armour> armours;
    protected List<AbstractMinion> abstractMinions;

    protected List<Strength> strengths;
    protected List<Weakness> weaknesses;

    protected List<Weapon> activeWeapons;
    protected Armour activeArmour;

    // Pool of total minion health that acts as a damage buffer
    protected int minionHealthPool;

    /**
     * Constructor.
     * @param name for the character
     * @param health of the character
     * @param power of the character
     */
    public AbstractCharacter(String name, int health, int power) {
        if (health < 0 || health > 5) {
            throw new IllegalArgumentException("Health must be between 0 and 5");
        }
        if (power < 1 || power > 5) {
            throw new IllegalArgumentException("Power must be between 1 and 5");
        }

        this.name = name;
        this.health = health;
        this.power = power;
        this.gold = 0;

        this.weapons = new ArrayList<>();
        this.armours = new ArrayList<>();
        this.abstractMinions = new ArrayList<>();
        this.strengths = new ArrayList<>();
        this.weaknesses = new ArrayList<>();
        this.activeWeapons = new ArrayList<>();
        this.minionHealthPool = 0;
    }

    // ... (métodos existentes sin cambios: getName, setSkill, chooseActiveEquipment, addWeapon, addArmour, addMinion, addStrength, addWeakness)

    public String getName() {
        return name;
    }

    public void setSkill(SpecialSkill skill) {
        this.skill = skill;
    }

    public void chooseActiveEquipment(ArrayList<Weapon> weapons, ArrayList<Armour> armours,
                                      ArrayList<Weapon> selectedWeapons, Armour selectedArmour) {
        this.weapons = weapons;
        this.armours = armours;
        this.activeWeapons = selectedWeapons;
        this.activeArmour = selectedArmour;
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public void addArmour(Armour armour) {
        armours.add(armour);
    }

    public void addMinion(AbstractMinion minion) {
        abstractMinions.add(minion);
    }

    public void addStrength(Strength strength) {
        strengths.add(strength);
    }

    public void addWeakness(Weakness weakness) {
        weaknesses.add(weakness);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(5, health));
    }

    public void reduceHealth(int amount) {
        health = Math.max(0, health - amount);
    }

    public int getPower() {
        return power;
    }

    public SpecialSkill getSpecialSkill() {
        return skill;
    }

    public int getStrengthsTotalModifier() {
        int total = 0;
        for (Strength strength : strengths) {
            total += strength.getValue();
        }
        return total;
    }

    public int getWeaknessesTotalModifier() {
        int total = 0;
        for (Weakness weakness : weaknesses) {
            total += weakness.getValue();
        }
        return total;
    }

    public List<Weapon> getActiveWeapons() {
        return activeWeapons;
    }

    public Armour getActiveArmour() {
        return activeArmour;
    }

    // ---------- Minion pool handling ----------

    /**
     * Initializes the minion health pool with the total health of all minions (recursive).
     * Must be called at the start of combat.
     */
    public void initMinionHealthPool() {
        this.minionHealthPool = getTotalMinionHealth();
    }

    /**
     * Calculates the total health of all minions recursively.
     */
    public int getTotalMinionHealth() {
        int total = 0;
        for (AbstractMinion minion : abstractMinions) {
            total += getMinionHealthRecursive(minion);
        }
        return total;
    }

    private int getMinionHealthRecursive(AbstractMinion minion) {
        int healthSum = minion.getHealth();
        if (minion instanceof Demon demon) {
            for (AbstractMinion sub : demon.getMinions()) {
                healthSum += getMinionHealthRecursive(sub);
            }
        }
        return healthSum;
    }

    /**
     * Applies damage to the minion health pool first.
     * @param damage incoming damage
     * @return remaining damage that exceeds the minion pool
     */
    public int applyDamageToMinions(int damage) {
        if (damage <= 0) return 0;

        if (damage >= minionHealthPool) {
            int overflow = damage - minionHealthPool;
            minionHealthPool = 0;
            return overflow;
        } else {
            minionHealthPool -= damage;
            return 0;
        }
    }

    public int getMinionHealthPool() {
        return minionHealthPool;
    }

    // ---------- Minion deep copy ----------

    public List<AbstractMinion> getMinionsCopy() {
        List<AbstractMinion> copy = new ArrayList<>();
        for (AbstractMinion minion : abstractMinions) {
            copy.add(minion.copy());
        }
        return copy;
    }

    public void restoreMinions(List<AbstractMinion> savedMinions) {
        this.abstractMinions.clear();
        for (AbstractMinion minion : savedMinions) {
            this.abstractMinions.add(minion);
        }
        // After restoring minions, reset the pool to its full value
        initMinionHealthPool();
    }

    // ---------- Abstract methods ----------

    public abstract int getAttackPotential(boolean useSkill);
    public abstract int getDefensePotential(boolean useSkill);

    public boolean isAlive() {
        return health > 0;
    }

    public int getGold() {
        return gold;
    }

    public void addGold(int amount) {
        this.gold += amount;
        if (this.gold < 0) this.gold = 0;
    }
}