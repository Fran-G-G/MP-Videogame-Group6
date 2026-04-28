package Game;

public class Originator {

    private Player p1;
    private Player p2;

    public Originator(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Memento save() {
        AbstractCharacter c1 = p1.getCharacter();
        AbstractCharacter c2 = p2.getCharacter();

        return new ConcreteMemento(
                c1.getHealth(),
                c2.getHealth(),
                c1.getMinionsCopy(),
                c2.getMinionsCopy()
        );
    }

    public void restore(Memento m) {
        ConcreteMemento cm = (ConcreteMemento) m;

        AbstractCharacter c1 = p1.getCharacter();
        AbstractCharacter c2 = p2.getCharacter();

        c1.setHealth(cm.getP1Health());
        c2.setHealth(cm.getP2Health());

        c1.restoreMinions(cm.getP1Minions());
        c2.restoreMinions(cm.getP2Minions());
    }
}