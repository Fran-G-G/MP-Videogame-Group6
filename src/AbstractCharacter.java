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

    protected List<Characteristic> strengths;
    protected List<Characteristic> weaknesses;

    protected List<Weapon> activeWeapons;
    protected Armour activeArmour;

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

    public void chooseActiveEquipment(ArrayList<Weapon> weapons, ArrayList<Armour> armours, ArrayList<Weapon> selectedWeapons, Armour selectedArmour) {
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

    public void addMinions(AbstractMinion abstractMinion) {
        abstractMinions.add(abstractMinion);
    }

    public void addStrength(Characteristic c) {
        strengths.add(c);
    }

    public void addWeakness(Characteristic c) {
        weaknesses.add(c);
    }

    public int getTotalAttack() {
        int total = power;

        if (skill != null) {
            total += skill.getAttack();
        }

        for (Weapon w : activeWeapons) {
            total += w.getAttackModifier();
        }

        return total;
    }

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

    public void reduceHealth() {
        if (health > 0) {
            health--;
        }
    }
}