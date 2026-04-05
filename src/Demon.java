import java.util.ArrayList;
import java.util.List;

/**
 * Demon minion, can have other minions (recursive structure).
 */
public class Demon extends AbstractMinion {

    private String pact;
    private List<AbstractMinion> abstractMinions;

    public Demon(String name, int health, String pact) {
        super(name, health);
        this.pact = pact;
        this.abstractMinions = new ArrayList<>();
    }

    public void addMinion(AbstractMinion abstractMinion) {
        abstractMinions.add(abstractMinion);
    }

    public List<AbstractMinion> getMinions() {
        return abstractMinions;
    }

    public String getPact() {
        return pact;
    }
}