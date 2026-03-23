import java.util.ArrayList;
import java.util.List;

/**
 * Demon minion, can have other minions (recursive structure).
 */
public class Demon extends Minion {

    private String pact;
    private List<Minion> minions;

    public Demon(String name, int health, String pact) {
        super(name, health);
        this.pact = pact;
        this.minions = new ArrayList<>();
    }

    public void addMinion(Minion minion) {
        minions.add(minion);
    }

    public List<Minion> getMinions() {
        return minions;
    }

    public String getPact() {
        return pact;
    }
}