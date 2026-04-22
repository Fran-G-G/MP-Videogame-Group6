import java.util.Random;

/**
 * Mediator that coordinates the combat between two players.
 */
public class CombatMediator implements Mediator {

    private final Player player1;
    private final Player player2;
    private Player current;
    private Player opponent;
    private final Random rand = new Random();
    private int roundNumber = 1;

    public CombatMediator(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
    }

    public void start() {
        System.out.println("\n======================================");
        System.out.println("        ⚔️  COMIENZA EL COMBATE  ⚔️");
        System.out.println("======================================\n");

        Originator originator = new Originator(player1, player2);
        Memento memento = originator.save();

        // Initialize minion health pools for both characters
        player1.getCharacter().initMinionHealthPool();
        player2.getCharacter().initMinionHealthPool();

        coinFlipAnimation();
        current = rand.nextBoolean() ? player1 : player2;
        opponent = getOpponent(current);
        System.out.println("🎲 " + current.getNick() + " ataca primero.\n");

        while (bothAlive()) {
            System.out.println("\n========== RONDA " + roundNumber + " ==========");
            printStatus();

            executeTurn(current, opponent);

            Player temp = current;
            current = opponent;
            opponent = temp;

            roundNumber++;
        }

        Player winner = player1.getCharacter().isAlive() ? player1 : player2;
        System.out.println("\n======================================");
        System.out.println("🏆 ¡" + winner.getNick() + " gana el combate! 🏆");
        System.out.println("======================================");

        originator.restore(memento);
        System.out.println("\nSalud y esbirros restaurados al estado previo al combate.");
    }

    private boolean bothAlive() {
        return player1.getCharacter().isAlive() && player2.getCharacter().isAlive();
    }

    private void executeTurn(Player attacker, Player defender) {
        AbstractCharacter attChar = attacker.getCharacter();
        AbstractCharacter defChar = defender.getCharacter();

        boolean useSkill = shouldUseSkill(attChar);

        if (useSkill) {
            System.out.println("✨ " + attacker.getNick() + " usa " + attChar.getSpecialSkill().getName() + "!");
        } else {
            System.out.println("⚔️ " + attacker.getNick() + " ataca.");
        }

        int attPotential = attChar.getAttackPotential(useSkill);
        int defPotential = defChar.getDefensePotential(false);

        System.out.println("💪 Potencial de ataque: " + attPotential + " → lanzando " + attPotential + " dados.");
        System.out.println("🛡️ Potencial de defensa: " + defPotential + " → lanzando " + defPotential + " dados.");

        int attSuccesses = Dice.rollSuccesses(attPotential);
        int defSuccesses = Dice.rollSuccesses(defPotential);

        System.out.println("🎲 Éxitos de ataque: " + attSuccesses + " | Éxitos de defensa: " + defSuccesses);

        if (useSkill && attChar instanceof Vampire v) {
            v.useDisciplineInCombat();
        }

        if (attSuccesses >= defSuccesses) {
            System.out.println("✅ ¡Ataque exitoso! " + defender.getNick() + " recibe 1 punto de daño.");
            applyDamage(defender);
        } else {
            System.out.println("❌ " + defender.getNick() + " bloquea el ataque.");
        }

        if (attSuccesses >= defSuccesses && attChar instanceof Vampire v) {
            v.recoverBlood();
            System.out.println("🩸 " + attacker.getNick() + " recupera 4 puntos de sangre.");
        }

        System.out.println("\nEstado tras la ronda:");
        printStatus();
    }

    private boolean shouldUseSkill(AbstractCharacter character) {
        SpecialSkill skill = character.getSpecialSkill();
        if (skill == null) return false;

        if (character instanceof Vampire v) {
            if (skill instanceof Discipline d) {
                return v.getBloodPoints() >= d.getBloodCost();
            }
        } else if (character instanceof Werewolf w) {
            return w.canUseGift();
        } else if (character instanceof Hunter) {
            return true;
        }
        return false;
    }

    private void applyDamage(Player defender) {
        AbstractCharacter defChar = defender.getCharacter();

        // Damage minion pool first
        int remainingDamage = defChar.applyDamageToMinions(1);
        if (remainingDamage > 0) {
            defChar.reduceHealth(remainingDamage);
        }

        if (defChar instanceof Werewolf w) {
            w.increaseRageOnDamage();
            System.out.println("🐺 La rabia de " + defender.getNick() + " aumenta a " + w.getRage() + ".");
        } else if (defChar instanceof Hunter h) {
            h.decreaseWillpowerOnDamage();
            System.out.println("🧠 La voluntad de " + defender.getNick() + " disminuye a " + h.getWill() + ".");
        }
    }

    private Player getOpponent(Player p) {
        return p == player1 ? player2 : player1;
    }

    public void printStatus() {
        System.out.println("\n------------------------------");
        printPlayerStatus(player1);
        printPlayerStatus(player2);
        System.out.println("------------------------------");
    }

    private void printPlayerStatus(Player p) {
        AbstractCharacter c = p.getCharacter();
        System.out.println(p.getNick());
        System.out.println("❤️ Salud: " + bar(c.getHealth(), 5));
        System.out.println("👹 Esbirros (escudo): " + bar(c.getMinionHealthPool(), c.getTotalMinionHealth()));
        if (c instanceof Vampire v) {
            System.out.println("🩸 Sangre: " + bar(v.getBloodPoints(), 10));
        } else if (c instanceof Werewolf w) {
            System.out.println("😡 Rabia: " + bar(w.getRage(), 3));
        } else if (c instanceof Hunter h) {
            System.out.println("🧠 Voluntad: " + bar(h.getWill(), 3));
        }
        System.out.println();
    }

    private String bar(int value, int max) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < max; i++) {
            sb.append(i < value ? "#" : "-");
        }
        sb.append("] ").append(value).append("/").append(max);
        return sb.toString();
    }

    public void coinFlipAnimation() {
        String[] frames = {"( )", "(/)", "(|)", "(\\)"};
        System.out.print("Lanzando moneda ");
        for (int i = 0; i < 8; i++) {
            System.out.print("\rLanzando moneda " + frames[i % frames.length]);
            waitTime(150);
        }
        System.out.println("\rMoneda lanzada.       ");
    }

    private void waitTime(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void notify(Player sender) {
        // Not used in this implementation
    }
}