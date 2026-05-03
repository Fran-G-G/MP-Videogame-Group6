package Game;

import java.io.Serializable;

/**
 * Ghoul minion.
 */
public class Ghoul extends AbstractMinion implements Serializable {

    private final int dependency; // Range: 1 - 5

    public Ghoul(String name, int health, int dependency) {
        super(name, health);
        if (dependency < 1 || dependency > 5) {
            throw new IllegalArgumentException("Dependency must be between 1 and 5");
        }
        this.dependency = dependency;
    }

    public int getDependency() {
        return dependency;
    }

    @Override
    public AbstractMinion copy() {
        return new Ghoul(name, health, dependency);
    }

    @Override
    public String getTypeName() {
        return "Ghoul";
    }
}