package Game;

import java.io.Serializable;

/**
 * Human minion.
 */
public class Human extends AbstractMinion implements Serializable {

    public enum Loyalty {
        HIGH, NORMAL, LOW
    }

    private Loyalty loyalty;

    public Human(String name, int health, Loyalty loyalty) {
        super(name, health);
        this.loyalty = loyalty;
    }

    public Loyalty getLoyalty() {
        return loyalty;
    }

    public void upgradeLoyalty() {
        if (loyalty == Loyalty.LOW) {
            loyalty = Loyalty.NORMAL;
        } else if (loyalty == Loyalty.NORMAL) {
            loyalty = Loyalty.HIGH;
        }
    }

    public void decreaseLoyalty() {
        if (loyalty == Loyalty.HIGH) {
            loyalty = Loyalty.NORMAL;
        } else if (loyalty == Loyalty.NORMAL) {
            loyalty = Loyalty.LOW;
        }
    }

    @Override
    public AbstractMinion copy() {
        return new Human(name, health, loyalty);
    }
}