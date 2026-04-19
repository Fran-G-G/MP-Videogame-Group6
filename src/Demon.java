import java.util.ArrayList;
import java.util.List;

/**
 * Demon minion, can have other minions (recursive structure).
 */
public class Demon extends AbstractMinion {

    private final String pact;
    private final List<AbstractMinion> demonsMinions;

    public Demon(String name, int health, String pact) {
        super(name, health);
        this.pact = pact;
        this.demonsMinions = new ArrayList<>();
    }

    public void addMinion(AbstractMinion minion) {
        demonsMinions.add(minion);
    }

    public List<AbstractMinion> getMinions() {
        return demonsMinions;
    }

    public String getPact() {
        return pact;
    }
}