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

    // 🔹 NUEVO: pool de vida de esbirros
    protected int totalMinionHealth;

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

    // ==============================
    // ⚔️ COMBATE
    // ==============================

    public boolean isAlive() {
        return health > 0;
    }

    public boolean canUseSkill() {
        return skill != null;
    }

    // 🔥 ATAQUE
    public int getTotalAttack(boolean useSkill) {
        int total = power;

        // armas
        for (Weapon w : activeWeapons) {
            total += w.getAttackModifier();
        }

        // armadura que da ataque
        if (activeArmour != null) {
            total += activeArmour.getAttackModifier();
        }

        // skill solo si se usa
        if (useSkill && skill != null) {
            total += skill.getAttack();
        }

        // fortalezas - debilidades
        total += getModifiers();

        return total;
    }

    // 🛡️ DEFENSA
    public int getTotalDefense(boolean useSkill) {
        int total = power;

        // armadura
        if (activeArmour != null) {
            total += activeArmour.getDefenseModifier();
        }

        // armas que dan defensa
        for (Weapon w : activeWeapons) {
            total += w.getDefenseModifier();
        }

        // skill
        if (useSkill && skill != null) {
            total += skill.getDefense();
        }

        total += getModifiers();

        return total;
    }

    // 🔹 MODIFICADORES (simple)
    private int getModifiers() {

        int mod = 0;

        for (Characteristic c : strengths) {
            mod += c.getValue();
        }

        for (Characteristic c : weaknesses) {
            mod -= c.getValue();
        }

        return mod;
    }

    // ==============================
    // 👹 ESBIRROS (POOL)
    // ==============================

    public void calculateTotalMinionHealth() {
        totalMinionHealth = 0;

        for (AbstractMinion m : abstractMinions) {
            totalMinionHealth += m.getHealth();
        }
    }

    public int getMinionHealth() {
        return totalMinionHealth;
    }

    public void setMinionHealth(int value) {
        totalMinionHealth = Math.max(0, value);
    }

    // ==============================
    // ❤️ VIDA
    // ==============================

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, health);
    }

    public void reduceHealth() {
        if (health > 0) {
            health--;
        }
    }

    public String getName() {
        return name;
    }
}