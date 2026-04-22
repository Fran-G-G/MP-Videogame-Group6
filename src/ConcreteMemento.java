import java.util.List;

public class ConcreteMemento implements Memento {

    private final int p1Health;
    private final int p2Health;
    private final List<AbstractMinion> p1Minions;
    private final List<AbstractMinion> p2Minions;

    public ConcreteMemento(int p1Health, int p2Health,
                           List<AbstractMinion> p1Minions,
                           List<AbstractMinion> p2Minions) {
        this.p1Health = p1Health;
        this.p2Health = p2Health;
        this.p1Minions = p1Minions;
        this.p2Minions = p2Minions;
    }

    public int getP1Health() { return p1Health; }
    public int getP2Health() { return p2Health; }
    public List<AbstractMinion> getP1Minions() { return p1Minions; }
    public List<AbstractMinion> getP2Minions() { return p2Minions; }
}