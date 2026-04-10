public class CombatHandler extends Handler {

    @Override
    public void handle(Request request) {

        Player p1 = request.getChallenger();
        Player p2 = request.getChallenged();

        System.out.println("\n==============================");
        System.out.println("     ⚔️  COMBATE INICIADO ⚔️");
        System.out.println("==============================\n");

        // 🔹 MEMENTO
        Originator originator = new Originator(p1, p2);
        Memento memento = originator.save();

        // 🔹 MEDIATOR
        CombatMediator mediator = new CombatMediator(p1, p2);

        mediator.coinFlipAnimation();

        Player current = mediator.selectFirst();

        int round = 1;

        while (p1.getCharacter().isAlive() && p2.getCharacter().isAlive()) {

            System.out.println("\n========== RONDA " + round + " ==========");

            mediator.printStatus();

            mediator.notify(current);

            current = mediator.getOpponent(current);

            round++;
        }

        mediator.printWinner();

        // 🔹 RESTORE
        originator.restore(memento);
    }
}