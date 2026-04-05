/**
 * Base class for all minions.
 */
public abstract class AbstractMinion {

    protected String name;
    protected int health; // Range: 1 - 3

    public AbstractMinion(String name, int health) {
        if (health < 1 || health > 3) {
            throw new IllegalArgumentException("La salud debe estar entre 1 y 3");
        }
        this.name = name;
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }
}