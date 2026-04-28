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
            throw new IllegalArgumentException("La salud debe estar entre 0 y 5");
        }
        if (power < 1 || power > 5) {
            throw new IllegalArgumentException("El poder debe estar entre 1 y 5");
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

    /**
     * @return the character's name.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param skill of the character.
     */
    public void setSkill(SpecialSkill skill) {
        this.skill = skill;
    }

    /**
     *
     * @param weapons the character has.
     * @param armours the character has.
     * @param selectedWeapons actual active weapon/s.
     * @param selectedArmour actual active armour.
     */
    public void chooseActiveEquipment(ArrayList<Weapon> weapons, ArrayList<Armour> armours, ArrayList<Weapon> selectedWeapons, Armour selectedArmour) {
        this.weapons = weapons;
        this.armours = armours;

        if (!selectedWeapons.isEmpty()) {
            this.activeWeapons = selectedWeapons;
        }

        if (selectedArmour != null) {
            this.activeArmour = selectedArmour;
        }
    }

    /**
     * @param weapon the user wants to add.
     */
    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    /**
     * @param armour the user wants to add.
     */
    public void addArmour(Armour armour) {
        armours.add(armour);
    }

    /**
     * @param minion the user wants to add.
     */
    public void addMinion(AbstractMinion minion) {
        abstractMinions.add(minion);
    }

    /**
     * @param strength add a strength to the character.
     */
    public void addStrength(Strength strength) {
        strengths.add(strength);
    }

    /**
     * @param weakness add a weakness to the character.
     */
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

    /**
     * @return the character's power.
     */
    public int getPower() {
        return power;
    }

    /**
     * @return the character's special skill.
     */
    public SpecialSkill getSpecialSkill() {
        return skill;
    }

    /**
     * @return total value of the strength modifiers of the character.
     */
    public int getStrengthsTotalModifier() {
        int total = 0;
        for (Strength strength : strengths) {
            total += strength.getValue();
        }

        return total;
    }

    /**
     * @return total value of the weakness modifiers of the character.
     */
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

    /**
     * @return the character's active armour.
     */
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

    public boolean hasActiveEquipment() {
        return activeWeapons != null && activeArmour !=null;
    }
}