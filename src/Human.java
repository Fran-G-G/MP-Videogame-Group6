/**
 * Human minion.
 */
public class Human extends AbstractMinion {

    public enum Loyalty {
        HIGH, NORMAL, LOW
    }

    private final Loyalty loyalty;

    public Human(String name, int health, Loyalty loyalty) {
        super(name, health);
        this.loyalty = loyalty;
    }

    public Loyalty getLoyalty() {
        return loyalty;
    }

    @Override
    public AbstractMinion copy() {
        return new Human(name, health, loyalty);
    }
}