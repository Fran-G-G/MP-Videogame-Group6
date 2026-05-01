package Game;

import java.util.Random;
import java.util.Scanner;

/**
 * Mediator that coordinates the combat between two players.
 * Features high-quality custom ASCII art, side-by-side view,
 * manual round advance (ENTER), and animated attacks.
 */
public class CombatMediator implements Mediator {

    private final Player player1;
    private final Player player2;
    private Player current;
    private Player opponent;
    private final Random rand = new Random();
    private int roundNumber = 1;
    private final Scanner scanner = new Scanner(System.in);

    public CombatMediator(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
    }

    /**
     * Starts the combat loop with visual effects.
     */
    public void start() {
        clearScreen();
        printIntro();

        Originator originator = new Originator(player1, player2);
        Memento memento = originator.save();

        player1.getCharacter().initMinionHealthPool();
        player2.getCharacter().initMinionHealthPool();

        coinFlipAnimation();
        current = rand.nextBoolean() ? player1 : player2;
        opponent = getOpponent(current);
        System.out.println("🎲 " + current.getNick() + " ataca primero.\n");
        waitMilliseconds(1000);

        boolean skipAll = false;
        while (bothAlive() && !skipAll) {
            System.out.println("\n========== RONDA " + roundNumber + " ==========");
            printSideBySide(player1, player2);

            executeTurn(current, opponent);

            Player temp = current;
            current = opponent;
            opponent = temp;

            roundNumber++;

            if (!skipAll) {
                System.out.print("\nPresiona ENTER para siguiente ronda, o ESPACIO+ENTER para saltar al final...");
                String input = scanner.nextLine();
                if (input.contains(" ")) {
                    skipAll = true;
                }
            }
        }

        Player winner = player1.getCharacter().isAlive() ? player1 : player2;
        Player loser = (winner == player1) ? player2 : player1;
        printVictoryScreen(winner, loser);

        originator.restore(memento);
        System.out.println("\nSalud y esbirros restaurados al estado previo al combate.");
    }

    private boolean bothAlive() {
        return player1.getCharacter().isAlive() && player2.getCharacter().isAlive();
    }

     void executeTurn(Player attacker, Player defender) {
        AbstractCharacter attChar = attacker.getCharacter();
        AbstractCharacter defChar = defender.getCharacter();

        System.out.println("\n" + attacker.getNick() + " ataca a " + defender.getNick() + "!");
        boolean useSkill = shouldUseSkill(attChar);

        if (useSkill) {
            System.out.println("✨ " + attacker.getNick() + " usa " + attChar.getSpecialSkill().getName() + "!");
            playSkillAnimation(attChar);
        } else {
            System.out.println("⚔️ " + attacker.getNick() + " ataca.");
            playAttackAnimation();
        }

        int attPotential = attChar.getAttackPotential(useSkill);
        int defPotential = defChar.getDefensePotential(false);

        System.out.println("💪 Potencial de ataque: " + attPotential + " → lanzando " + attPotential + " dados.");
        System.out.println("🛡️ Potencial de defensa: " + defPotential + " → lanzando " + defPotential + " dados.");

        playDiceAnimation(attPotential, "ataque");
        int attSuccesses = Dice.rollSuccesses(attPotential);
        playDiceAnimation(defPotential, "defensa");
        int defSuccesses = Dice.rollSuccesses(defPotential);

        System.out.println("🎲 Éxitos de ataque: " + attSuccesses + " | Éxitos de defensa: " + defSuccesses);
        waitMilliseconds(500);

        if (useSkill && attChar instanceof Vampire v) {
            v.useDisciplineInCombat();
        }

        if (attSuccesses >= defSuccesses) {
            System.out.println("✅ ¡Ataque exitoso! " + defender.getNick() + " recibe 1 punto de daño.");
            applyDamage(defender);
        } else {
            System.out.println("❌ " + defender.getNick() + " bloquea el ataque.");
            playBlockAnimation();
        }

        if (attSuccesses >= defSuccesses && attChar instanceof Vampire v) {
            v.recoverBlood();
            System.out.println("🩸 " + attacker.getNick() + " recupera 4 puntos de sangre.");
        }

        System.out.println("\nEstado tras la ronda:");
        printSideBySide(player1, player2);
    }

    // ---------- AI decision ----------
     boolean shouldUseSkill(AbstractCharacter character) {
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

     void applyDamage(Player defender) {
        AbstractCharacter defChar = defender.getCharacter();

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

    // =================================================================
    //                         SIDE-BY-SIDE VIEW
    // =================================================================

    private void printSideBySide(Player leftPlayer, Player rightPlayer) {
        AbstractCharacter left = leftPlayer.getCharacter();
        AbstractCharacter right = rightPlayer.getCharacter();

        String[] leftArt = getCharacterArt(left);
        String[] rightArt = getCharacterArt(right);

        // Stats for each side
        String leftStats1 = "❤️ Salud: " + bar(left.getHealth(), 5);
        String leftStats2 = "👹 Esbirros: " + bar(left.getMinionHealthPool(), left.getTotalMinionHealth());
        String leftStats3 = getResourceLine(left);
        String rightStats1 = "❤️ Salud: " + bar(right.getHealth(), 5);
        String rightStats2 = "👹 Esbirros: " + bar(right.getMinionHealthPool(), right.getTotalMinionHealth());
        String rightStats3 = getResourceLine(right);

        // Combine art + stats
        String[] leftBlock = combineArtAndStats(leftArt, leftStats1, leftStats2, leftStats3);
        String[] rightBlock = combineArtAndStats(rightArt, rightStats1, rightStats2, rightStats3);

        int maxLines = Math.max(leftBlock.length, rightBlock.length);

        System.out.println();
        for (int i = 0; i < maxLines; i++) {
            String leftLine = i < leftBlock.length ? leftBlock[i] : "";
            String rightLine = i < rightBlock.length ? rightBlock[i] : "";
            System.out.printf("%-80s  %s%n", leftLine, rightLine);
        }
        System.out.println();
    }

    private String[] combineArtAndStats(String[] art, String... stats) {
        String[] result = new String[art.length + stats.length];
        System.arraycopy(art, 0, result, 0, art.length);
        int idx = art.length;
        for (String stat : stats) {
            result[idx++] = stat;
        }
        return result;
    }

    private String getResourceLine(AbstractCharacter c) {
        if (c instanceof Vampire v) {
            return "🩸 Sangre: " + bar(v.getBloodPoints(), 10);
        } else if (c instanceof Werewolf w) {
            return "😡 Rabia: " + bar(w.getRage(), 3);
        } else if (c instanceof Hunter h) {
            return "🧠 Voluntad: " + bar(h.getWill(), 3);
        }
        return "";
    }

    private String bar(int value, int max) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < max; i++) {
            sb.append(i < value ? "▓" : "░");
        }
        sb.append("] ").append(value).append("/").append(max);
        return sb.toString();
    }

    // =================================================================
    //              CUSTOM CHARACTER ART (provided by the user)
    // =================================================================

    private String[] getCharacterArt(AbstractCharacter c) {
        if (c instanceof Vampire) {
            return VAMPIRE_ART;
        } else if (c instanceof Werewolf) {
            return WEREWOLF_ART;
        } else { // Hunter
            return HUNTER_ART;
        }
    }

    // ASCII art constants
    private static final String[] VAMPIRE_ART = {
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⡀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣠⣤⣶⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⣦⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣿⣿⡄⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠴⢾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡀⠀⠀⠀⠀⠀⠀⢠⣿⣿⣿⣿⡄⠀",
            "⣤⡀⠀⠀⠀⠀⠀⠀⠀⢀⣴⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠿⣿⣿⡄⠀⠀⠀⢀⣴⣿⠟⠀⢻⣿⣷⠀",
            "⣿⣿⣷⣦⣄⠀⠀⢀⣴⣿⣿⣿⣿⣿⣿⣿⡿⠟⠉⠉⠉⠉⠙⠻⢿⣿⠃⠀⠀⠀⠹⣷⠀⣀⣴⣿⠟⠃⠀⠀⠀⣿⣿⡆",
            "⣿⣿⡏⠙⠿⣿⣶⣬⡛⣿⣿⣿⣿⣿⣿⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠀⠀⠀⠀⠀⢹⣾⠿⠛⠁⠀⠀⠀⠀⠀⢸⣿⡇",
            "⢿⣿⡇⠀⠀⠀⠉⠛⠻⣿⣿⣿⣿⣿⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣇⠀⠀⣦⠀⠀⠀⠀⠀⠘⣿⣷",
            "⢸⣿⣧⠀⠀⠀⠀⣤⠀⠈⢿⣿⣿⣿⣿⣆⠀⠀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣾⣿⡆⢰⣿⣧⠀⠀⠀⠀⠀⣿⣿",
            "⠈⣿⣿⡄⠀⠀⠀⣿⣷⡀⠈⢿⣿⣿⣿⡟⢠⣾⣿⣿⣧⡀⠀⠀⠀⠀⠀⠀⠀⢠⣿⣿⣿⢀⣾⠁⢻⡆⠀⠀⠀⠀⣿⣿",
            "⠀⠸⣿⣧⠀⠀⠀⣿⠘⢿⣦⠘⣿⣿⣿⡄⠻⠛⣻⣿⣿⣿⣦⡀⠀⠀⠀⡀⣠⣿⣿⠻⣿⣾⠃⣸⡌⣷⠀⠀⠀⠀⣿⣿",
            "⠀⠀⢿⣿⡆⠀⠀⣿⠠⡄⠙⢿⣿⣿⣿⣧⠀⢰⣿⠁⣸⣿⣿⢿⣷⣿⠘⣿⡿⡏⣿⢀⣿⢷⣴⡟⠇⣿⠀⠀⠀⢸⣿⡟",
            "⠀⠀⠘⣿⣷⠀⠀⢿⡆⢿⢦⣀⠈⢻⣿⣿⡆⢻⣿⠀⢿⣸⣿⠀⣹⢻⣇⠙⢷⣿⣿⣿⠏⠀⢿⢿⢰⡟⠀⠀⠀⣾⣿⠇",
            "⠀⠀⠀⠸⣿⣧⠀⠘⣷⡈⢠⣿⠷⠈⣿⣿⣷⠈⢿⣦⣼⣿⣧⣴⡟⠋⠻⡄⠈⣿⡿⠿⠃⢀⣿⣰⡟⠁⠀⠀⣸⣿⡟⠀",
            "⠀⠀⠀⠀⠈⢿⣷⡄⠈⢻⣌⠳⠀⢀⣾⣧⠈⠀⠀⠀⠒⠴⠖⠋⠀⢷⠢⠄⠀⢸⡆⢀⣀⣿⡏⠉⠀⠀⠀⢠⣿⡟⠁⠀",
            "⠀⠀⠀⠀⠀⠀⠙⣿⣦⡀⠉⠻⠶⠿⠋⢿⡆⠀⠳⣤⡀⢶⣴⠀⠀⠘⠓⠦⣄⣸⠁⣸⢻⡿⠷⠀⠀⠀⢠⣿⡟⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠈⠻⣷⣄⡀⠀⠀⠀⠈⣿⡄⠀⠀⢳⡈⠉⠙⡶⢤⣀⠀⠈⣋⡴⢻⠀⣧⡇⠀⠀⢠⣿⡟⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠻⣿⣦⡀⠀⠀⠘⣷⡀⠀⠀⢷⠀⠀⢱⣸⠋⣉⣉⣉⣹⡟⢰⣿⠁⠀⢠⣿⡟⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⠿⣷⣄⡀⠈⠛⠷⣦⣌⣧⠀⠀⢻⠀⠈⠉⠁⠀⠁⢸⡇⠀⣠⣿⠟⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠻⢿⣶⣤⣀⠀⠉⠛⠷⣶⣤⣀⠀⠀⠀⡦⢀⡞⠀⣴⡟⠁⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⠛⠿⢶⣤⣀⡀⠉⠛⠿⢶⠶⠛⢋⣡⡾⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠛⠛⠲⠶⠶⠶⠟⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"
    };

    private static final String[] WEREWOLF_ART = {
            "⠀⠀⠀⠀⠀⠀⠀⢀⣤⣤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⣰⡆⠀⣿⡟⣁⣀⠀⠀⠀⠸⣷⣤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠹⣿⣶⣿⣿⠟⠙⠓⠀⠀⠀⠻⣿⣿⣿⣿⣿⡿⣿⣿⣿⣿⠃⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠈⢻⣿⣿⡄⠀⢀⣴⣶⣿⣿⣿⣿⣿⣿⠏⢀⠙⠋⠀⠉⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠸⣿⣿⣷⣴⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣿⣷⣾⡿⠀⠀⠀⠀⠀",
            "⠀⠀⠀⣴⡀⠀⠀⠛⠛⠛⠻⣿⣿⣿⣿⣿⣿⣿⡿⠋⠉⠀⠀⠀⣀⣀⣀⠀⠀⠀",
            "⠀⠀⢰⣿⣇⣠⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⢿⣿⣦⣀⠀⣴⣿⣿⠋⠉⠀⠀⠀",
            "⠀⠀⣈⣿⣿⣿⠀⠀⠀⠀⠀⣼⣿⣿⣿⣿⠇⠈⢿⣿⣿⣿⡿⠿⣿⣿⠿⣷⡄⠀",
            "⠀⠀⠻⣿⣿⣿⣧⡀⠀⣠⣾⣿⣿⣿⣿⣿⣦⣤⣀⠉⠉⠀⠀⠀⠘⣿⠀⠘⠃⠀",
            "⠀⠀⠀⠈⠉⠛⠻⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣆⠀⠀⠀⠀⠋⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⣀⠀⠀⣿⣿⣿⣿⡟⠀⠀⠈⠻⣿⣿⣿⣿⣇⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⢠⣾⣿⣿⣶⣿⣿⣿⠏⠀⠀⠀⢀⣴⣿⠿⠿⠿⠟⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⢠⣿⣿⣿⠉⠙⠛⠋⠁⠀⠀⠀⢠⣾⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⣾⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠐⠛⠛⠛⠛⠓⠀⠀⠀⠀⠀⠀⠀⠈⠛⠛⠛⠛⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"
    };

    private static final String[] HUNTER_ART = {
            "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠉⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿",
            "⣿⣿⣿⣿⡿⣿⣿⣿⣿⣿⣿⠃⣴⣆⠈⢿⣿⣿⣿⢻⣿⣿⣿⣿⠿⢻⣿⣿⣿⣿",
            "⣿⣿⣿⣿⡇⠹⣿⣿⣿⣿⠃⣼⣿⣿⣆⠈⢿⣿⡏⢸⣿⡿⠛⣡⠀⣸⣿⣿⣿⣿",
            "⣿⣿⣿⣿⡇⢰⡘⣿⣿⠇⣼⣿⣿⣿⣿⣦⠈⢿⣡⢸⠟⣡⣾⣿⠀⣿⣿⣿⣿⣿",
            "⣿⣿⡿⣿⡇⢸⣿⡹⠛⣼⣿⣿⣿⣿⣿⣿⣷⣌⣿⣨⣾⣿⣿⡏⢸⣿⣿⠟⢉⣾",
            "⣟⢿⣧⠹⡇⢸⣿⣷⣰⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠼⠋⣠⢀⣾⣿",
            "⣿⡄⠻⡰⡄⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣁⣴⡿⢁⡿⢟⣽",
            "⣿⣿⡠⠁⣿⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠁⡋⣠⣾⣿",
            "⣿⣿⣷⢳⣹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣣⡞⣵⣿⣿⣿",
            "⣿⣿⣿⣏⣿⣿⣯⠻⢯⠻⠟⢿⣿⣿⡿⠻⠛⠡⠛⠡⢿⣻⣿⣿⢏⣼⣿⣿⣿⣿",
            "⣿⣿⣿⣿⣼⣿⠀⠀⠀⠀⠀⠀⠙⠁⠀⠀⠀⠀⠀⠐⠻⣿⣿⢏⣾⣿⣿⣿⣿⣿",
            "⣿⣿⣿⣿⠷⠁⠀⢈⠢⢄⠀⠀⠀⠀⠀⠀⣠⣴⢈⠀⠈⣿⠟⢺⣿⣿⣿⣿⣿⣿",
            "⣿⣿⣿⡟⠀⠀⠀⠁⠐⢾⠗⠀⠀⠀⠀⠹⢰⡟⠂⠆⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿",
            "⣿⣿⣿⣷⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠄⠀⠁⠀⠀⢐⣴⣿⣿⣿⣿⣿⣿",
            "⣿⣿⣿⣿⣿⣷⣄⠀⠀⠀⠀⠀⠌⠀⠀⠀⠀⠀⠀⠀⣤⣶⣿⣿⣿⣿⣿⣿⣿⣿",
            "⣿⣿⣿⣿⣿⣿⣿⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠠⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿",
            "⣿⣿⣿⣿⣿⣿⣿⣿⣄⡀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿",
            "⣿⣿⣿⣿⣿⡿⠟⠃⠉⠛⠷⣦⣄⣀⣤⡴⠾⠛⠁⠀⠉⠛⠿⣿⣿⣿⣿⣿⣿⣿",
            "⣿⣿⠛⠉⠁⠀⠀⠀⠀⠀⠀⠀⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⠻⢿⣿⣿",
            "⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿",
            "⣿⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣘⣿⣿⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣹⣿"
    };

    private static final String[] TROPHY_ART = {
            "⠀⠀⠀⠀⢀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⠀⠀⠀⠀",
            "⢠⣤⣤⣤⣼⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣄⣤⣤⣠",
            "⢸⠀⡶⠶⠾⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡷⠶⠶⡆⡼",
            "⠈⡇⢷⠀⠀⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⠇⠀⢸⢁⡗",
            "⠀⢹⡘⡆⠀⢹⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡸⠀⢀⡏⡼⠀",
            "⠀⠀⢳⡙⣆⠈⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⠇⢀⠞⡼⠁⠀",
            "⠀⠀⠀⠙⣌⠳⣼⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣞⡴⣫⠞⠀⠀⠀",
            "⠀⠀⠀⠀⠈⠓⢮⣻⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡼⣩⠞⠉⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠉⠛⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⠞⠋⠁⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠳⢤⣀⠀⠀⠀⠀⢀⣠⠖⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⡇⢸⡏⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⠖⠒⠓⠚⠓⠒⣦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⣀⣠⣞⣉⣉⣉⣉⣉⣉⣉⣉⣉⣉⣙⣆⣀⡀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠓⠲⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⠖⠃⠀⠀⠀⠀⠀⠀"
    };

    private static final String[] SKULL_ART = {
            "⠀⠀⠀⠀⢀⣀⣤⣤⣤⣤⣄⡀⠀⠀⠀⠀",
            "⠀⢀⣤⣾⣿⣾⣿⣿⣿⣿⣿⣿⣷⣄⠀⠀",
            "⢠⣾⣿⢛⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡀",
            "⣾⣯⣷⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧",
            "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿",
            "⣿⡿⠻⢿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠻⢿⡵",
            "⢸⡇⠀⠀⠉⠛⠛⣿⣿⠛⠛⠉⠀⠀⣿⡇",
            "⢸⣿⣀⠀⢀⣠⣴⡇⠹⣦⣄⡀⠀⣠⣿⡇",
            "⠈⠻⠿⠿⣟⣿⣿⣦⣤⣼⣿⣿⠿⠿⠟⠀",
            "⠀⠀⠀⠀⠸⡿⣿⣿⢿⡿⢿⠇⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠈⠁⠈⠁⠀⠀⠀⠀⠀⠀"
    };

    // =================================================================
    //                INTRO WITH CROSSED SWORDS
    // =================================================================.

    private void printIntro() {
        System.out.println();
        String[] introArt = {
                " n                                                                 :.",
                " E%                                                                :\"5",
                "z  %                                                              :\" `",
                "K   \":                                                           z   R",
                " ?     %.                                                       :^    J",
                " \".    ^s                                                     f     :~",
                "  '+.    #L                                                 z\"    .*",
                "    '+     %L                                             z\"    .~",
                "      \":    '%.                                         .#     +",
                "        \":    ^%.                                     .#`    +\"",
                "          #:    \"n                                  .+`   .z\"",
                "            #:    \":                               z`    +\"",
                "              %:   `*L                           z\"    z\"",
                "                *:   ^*L                       z*   .+\"",
                "                  \"s   ^*L                   z#   .*\"",
                "                    #s   ^%L               z#   .*\"",
                "                      #s   ^%L           z#   .r\"",
                "                        #s   ^%.       u#   .r\"",
                "                          #i   '%.   u#   .@\"",
                "                            #s   ^%u#   .@\"",
                "                              #s x#   .*\"",
                "                               x#`  .@%.",
                "                             x#`  .d\"  \"%.",
                "                           xf~  .r\" #s   \"%.",
                "                     u   x*`  .r\"     #s   \"%.  x.",
                "                     %Mu*`  x*\"         #m.  \"%zX\"",
                "                     :R(h x*              \"h..*dN.",
                "                   u@NM5e#>                 7?dMRMh.",
                "                 z$@M@$#\"#\"                 *\"\"*@MM$hL",
                "               u@@MM8*                          \"*$M@Mh.",
                "             z$RRM8F\"                             \"N8@M$bL",
                "            5`RM$#                                  'R88f)R",
                "            'h.$\"                                     #$x*"
        };
        for (String line : introArt) {
            System.out.println(line);
            waitMilliseconds(50); // adjust speed as you like
        }
        System.out.println("\n======================================");
        System.out.println("        ⚔️  COMIENZA EL COMBATE  ⚔️");
        System.out.println("======================================\n");
        waitMilliseconds(1500);
    }


    // =================================================================
    //                 VICTORY SCREEN
    // =================================================================

    private void printVictoryScreen(Player winner, Player loser) {
        System.out.println("\n======================================");
        System.out.println("🏆 ¡" + winner.getNick() + " gana el combate! 🏆");
        System.out.println("======================================\n");

        int maxLines = Math.max(TROPHY_ART.length, SKULL_ART.length);
        for (int i = 0; i < maxLines; i++) {
            String leftLine = i < TROPHY_ART.length ? TROPHY_ART[i] : "";
            String rightLine = i < SKULL_ART.length ? SKULL_ART[i] : "";
            System.out.printf("%-80s  %s%n", leftLine, rightLine);
        }
        System.out.println("\n     🏆 GANADOR: " + winner.getNick() + "       💀 PERDEDOR: " + loser.getNick());
        waitMilliseconds(2000);
    }

    // =================================================================
    //                  ANIMATIONS (skill, dice, block)
    // =================================================================

    private void playAttackAnimation() {
        System.out.print("Atacando");
        for (int i = 0; i < 4; i++) {
            System.out.print(" ⚔️");
            waitMilliseconds(120);
        }
        System.out.println(" ⚔️ ¡Golpe!");
        waitMilliseconds(200);
    }

    private void playSkillAnimation(AbstractCharacter c) {
        System.out.print("Canalizando poder");
        for (int i = 0; i < 5; i++) {
            System.out.print(" ✨");
            waitMilliseconds(100);
        }
        System.out.println();
        if (c instanceof Vampire) {
            System.out.println("    🩸 ¡Disciplina vampírica!");
        } else if (c instanceof Werewolf) {
            System.out.println("    🐺 ¡Don de la bestia!");
        } else {
            System.out.println("    🧠 ¡Talento desatado!");
        }
        waitMilliseconds(200);
    }

    private void playDiceAnimation(int count, String type) {
        System.out.print("Lanzando dados de " + type + ": ");
        for (int i = 0; i < count; i++) {
            int face = rand.nextInt(6) + 1;
            System.out.print("[" + face + "] ");
            waitMilliseconds(100);
        }
        System.out.println();
        waitMilliseconds(200);
    }

    private void playBlockAnimation() {
        System.out.print("🛡️ Bloqueo ");
        for (int i = 0; i < 2; i++) {
            System.out.print("· ");
            waitMilliseconds(150);
        }
        System.out.println("¡Defensa exitosa!");
    }

    // =================================================================
    //                    COIN FLIP
    // =================================================================

    public void coinFlipAnimation() {
        String[] frames = {"( )", "(/)", "(|)", "(\\)"};
        System.out.print("Lanzando moneda ");
        for (int i = 0; i < 8; i++) {
            System.out.print("\rLanzando moneda " + frames[i % frames.length]);
            waitMilliseconds(150);
        }
        System.out.println("\rMoneda lanzada.       ");
        waitMilliseconds(500);
    }

    // =================================================================
    //                  UTILITY METHODS
    // =================================================================

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void waitMilliseconds(int ms) {
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