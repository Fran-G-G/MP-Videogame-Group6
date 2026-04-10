import java.util.Random;

public class CombatMediator implements Mediator {

    private Player p1;
    private Player p2;
    private Player current;

    private Random rand = new Random();

    public CombatMediator(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Player selectFirst() {
        current = rand.nextBoolean() ? p1 : p2;
        return current;
    }

    public Player getOpponent(Player p) {
        return (p == p1) ? p2 : p1;
    }

    // 🧠 IA
    public boolean chooseAction(Player attacker, Player defender) {

        int normal = calculateDamage(attacker, defender, false);

        if (attacker.canUseSkill()) {
            int skill = calculateDamage(attacker, defender, true);
            return skill > normal;
        }

        return false;
    }

    // ⚔️ CÁLCULO
    public int calculateDamage(Player attacker, Player defender, boolean useSkill) {

        int attack = attacker.getCharacter().getTotalAttack(useSkill);
        int defense = defender.getCharacter().getTotalDefense(useSkill);

        int dmg = attack - defense;

        return Math.max(0, dmg);
    }

    // 💥 DAÑO + ESBIRROS
    public void applyDamage(Player target, int damage) {

        int minions = target.getCharacter().getMinionHealth();

        if (minions > 0) {

            minions -= damage;

            if (minions < 0) {
                int overflow = Math.abs(minions);
                target.getCharacter().setMinionHealth(0);
                target.getCharacter().setHealth(
                        target.getCharacter().getHealth() - overflow
                );
            } else {
                target.getCharacter().setMinionHealth(minions);
            }

        } else {
            target.getCharacter().setHealth(
                    target.getCharacter().getHealth() - damage
            );
        }
    }

    // 🔁 MEDIATOR
    @Override
    public void notify(Player sender) {

        Player opponent = getOpponent(sender);

        boolean useSkill = chooseAction(sender, opponent);

        int damage = calculateDamage(sender, opponent, useSkill);

        printAction(sender, useSkill, damage);

        applyDamage(opponent, damage);
    }

    // 🖥️ OUTPUT

    public void printStatus() {
        System.out.println("\n------------------------------");
        printPlayer(p1);
        printPlayer(p2);
        System.out.println("------------------------------");
    }

    private void printPlayer(Player p) {

        var c = p.getCharacter();

        System.out.println(p.getNick());
        System.out.println("❤️ " + bar(c.getHealth(), 5));
        System.out.println("👹 " + bar(c.getMinionHealth(), 5));
        System.out.println();
    }

    public void printAction(Player p, boolean skill, int dmg) {

        if (skill) {
            System.out.println("🔥 " + p.getNick() + " usa habilidad!");
        } else {
            System.out.println("⚔️ " + p.getNick() + " ataca!");
        }

        System.out.println("💥 Daño: " + dmg);
    }

    public void printWinner() {

        System.out.println("\n==============================");

        if (p1.getCharacter().isAlive()) {
            System.out.println("🏆 GANA " + p1.getNick());
        } else {
            System.out.println("🏆 GANA " + p2.getNick());
        }

        System.out.println("==============================");
    }

    // 🎲 MONEDA ASCII

    public void coinFlipAnimation() {

        String[] frames = {"( )", "(/)", "(|)", "(\\)"};

        System.out.println("Lanzando moneda...");

        for (int i = 0; i < 10; i++) {
            System.out.print("\r" + frames[i % frames.length]);
            waitTime();
        }

        System.out.println("\n");
    }

    private String bar(int value, int max) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < max; i++) {
            sb.append(i < value ? "#" : "-");
        }
        sb.append("]");
        return sb.toString();
    }

    public void waitTime() {
        try {
            Thread.sleep(300);
        } catch (Exception e) {}
    }
}