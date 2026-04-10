public class ConcreteMemento implements Memento {

    private final int p1Health;
    private final int p2Health;
    private final int p1Minions;
    private final int p2Minions;

    public ConcreteMemento(int p1Health, int p2Health, int p1Minions, int p2Minions) {
        this.p1Health = p1Health;
        this.p2Health = p2Health;
        this.p1Minions = p1Minions;
        this.p2Minions = p2Minions;
    }

    public int getP1Health() { return p1Health; }
    public int getP2Health() { return p2Health; }
    public int getP1Minions() { return p1Minions; }
    public int getP2Minions() { return p2Minions; }
}