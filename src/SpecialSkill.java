/**
 * Base class for all special skills.
 */
public abstract class SpecialSkill {

    protected String name;
    protected int attack;
    protected int defense;

    public SpecialSkill(String name, int attack, int defense) {
        if (attack < 1 || attack > 3 || defense < 1 || defense > 3) {
            throw new IllegalArgumentException("Ataque y defensa deben estar entre 1 y 3");
        }
        this.name = name;
        this.attack = attack;
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }
}