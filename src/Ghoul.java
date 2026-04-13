/**
 * Ghoul minion.
 */
public class Ghoul extends AbstractMinion {

    private final int dependency; // Range: 1 - 5

    public Ghoul(String name, int health, int dependency) {
        super(name, health);

        if (dependency < 1 || dependency > 5) {
            throw new IllegalArgumentException("La dependencia debe estar entre 1 y 5");
        }

        this.dependency = dependency;
    }

    public int getDependency() {
        return dependency;
    }
}