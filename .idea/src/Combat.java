import java.util.Random;

/**
 * Handles combat between two players.
 */
public class Combat {

    private Player player1;
    private Player player2;

    private Random random;

    public Combat(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.random = new Random();
    }

    /**
     * Executes the full combat
     */
    public void start() {
        AbstractCharacter c1 = player1.getCharacter();
        AbstractCharacter c2 = player2.getCharacter();

        System.out.println("⚔️ ¡Comienza el combate!");

        int round = 1;

        while (c1.getHealth() > 0 && c2.getHealth() > 0) {

            System.out.println("\n--- Ronda " + round + " ---");

            int attack1 = calculateSuccesses(c1.getTotalAttack());
            int defense2 = calculateSuccesses(c2.getTotalDefense());

            int attack2 = calculateSuccesses(c2.getTotalAttack());
            int defense1 = calculateSuccesses(c1.getTotalDefense());

            // Player1 attacks Player2
            if (attack1 >= defense2) {
                c2.reduceHealth();
                System.out.println(player1.getNick() + " golpea a " + player2.getNick());
            }

            // Player2 attacks Player1
            if (attack2 >= defense1) {
                c1.reduceHealth();
                System.out.println(player2.getNick() + " golpea a " + player1.getNick());
            }

            System.out.println("Salud " + player1.getNick() + ": " + c1.getHealth());
            System.out.println("Salud " + player2.getNick() + ": " + c2.getHealth());

            round++;
        }

        // Resultado
        if (c1.getHealth() <= 0 && c2.getHealth() <= 0) {
            System.out.println("🤝 Empate");
        } else if (c1.getHealth() <= 0) {
            System.out.println("🏆 Gana " + player2.getNick());
        } else {
            System.out.println("🏆 Gana " + player1.getNick());
        }
    }

    /**
     * Rolls dice and counts successes (5 or 6)
     */
    private int calculateSuccesses(int potential) {
        int successes = 0;

        for (int i = 0; i < potential; i++) {
            int dice = random.nextInt(6) + 1;

            if (dice >= 5) {
                successes++;
            }
        }

        return successes;
    }
}