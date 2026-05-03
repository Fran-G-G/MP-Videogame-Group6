package Game;

import java.io.Serializable;

/**
 * Base class for all minions.
 */
public abstract class AbstractMinion implements Serializable {

    protected String name;
    protected int health; // Range: 1 - 3

    public AbstractMinion(String name, int health) {
        if (health < 1 || health > 3) {
            throw new IllegalArgumentException("Health must be between 1 and 3");
        }
        this.name = name;
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, health);
    }

    public String getName() {
        return name;
    }

    /**
     * Creates a deep copy of this minion.
     * @return a new instance with the same attributes.
     */
    public abstract AbstractMinion copy();

    public abstract String getTypeName();
}