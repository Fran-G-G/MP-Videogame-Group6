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
    }

    /**
     * @return the character's name.
     */
    public String getName() {
        return name;
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

        this.activeWeapons = selectedWeapons;
        this.activeArmour = selectedArmour;
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

    /**
     * @return the attack of the character.
     */
    public int getTotalAttack() {
        int total = power;

        if (skill != null) {
            total += skill.getAttackValue();
        }

        for (Weapon w : activeWeapons) {
            total += w.getAttackModifier();
        }

        return total;
    }

    /**
     * @return the defense of the character.
     */
    public int getTotalDefense() {
        int total = 0;

        if (skill != null) {
            total += skill.getDefense();
        }

        if (activeArmour != null) {
            total += activeArmour.getDefenseModifier();
        }

        return total;
    }

    public int getHealth() {
        return health;
    }

    /**
     * Reduce the character's health.
     */
    public void reduceHealth() {
        if (health > 0) {
            health--;
        }
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
        for (int i=0; i < weaknesses.toArray().length; i++) {
            total += weaknesses.get(i).getValue();
        }

        return total;
    }

    /**
     * @return the character's active weapons.
     */
    public ArrayList<Weapon> getActiveWeapons() {
        return (ArrayList<Weapon>) activeWeapons;
    }

    /**
     * @return the character's active armour.
     */
    public Armour getActiveArmour() {
        return activeArmour;
    }
}