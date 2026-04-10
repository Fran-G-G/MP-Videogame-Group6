public class Originator {

    private Player p1;
    private Player p2;

    public Originator(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Memento save() {
        return new ConcreteMemento(
                p1.getCharacter().getHealth(),
                p2.getCharacter().getHealth(),
                p1.getCharacter().getMinionHealth(),
                p2.getCharacter().getMinionHealth()
        );
    }

    public void restore(Memento m) {

        ConcreteMemento cm = (ConcreteMemento) m;

        p1.getCharacter().setHealth(cm.getP1Health());
        p2.getCharacter().setHealth(cm.getP2Health());

        p1.getCharacter().setMinionHealth(cm.getP1Minions());
        p2.getCharacter().setMinionHealth(cm.getP2Minions());
    }
}