import java.util.Scanner;

/**
 * Initializes and runs the game in terminal mode.
 */
public class GameStarter {

    private Scanner scanner;

    public GameStarter() {
        scanner = new Scanner(System.in);
    }

    public void run() {

        System.out.println("Bienvenido al juego.\n");

        // Create players
        Player p1 = createPlayer(1);
        Player p2 = createPlayer(2);

        // Create characters
        System.out.println("\nCreación de personajes...\n");

        p1.setCharacter(createCharacter(p1));
        p2.setCharacter(createCharacter(p2));

        // Create and validate challenge
        ChallengeHandler challengeHandler = new ChallengeHandler() {
            @Override
            public void handle(ChallengeHandler challenge) {

            }
        };
        Admin admin = new Admin("Admin", "admin", "12345678");

        admin.validateChallenge(challengeHandler);


        System.out.println("\nDesafío aceptado. ¡Empieza el combate!\n");

        // Start combat
        Combat combat = new Combat(p1, p2);
        combat.start();
    }

    /**
     * Creates a player via terminal input.
     */
    private Player createPlayer(int number) {

        System.out.println("Jugador " + number);

        System.out.print("Nombre: ");
        String name = scanner.nextLine();

        System.out.print("Nick: ");
        String nick = scanner.nextLine();

        System.out.print("Password (8-12 chars): ");
        String pass = scanner.nextLine();

        return new Player(name, nick, pass);
    }

    /**
     * Creates a character for a player.
     */
    private AbstractCharacter createCharacter(Player player) {

        System.out.println("\n" + player.getNick() + ", elige tu tipo:");
        System.out.println("1. Vampiro");
        System.out.println("2. Hombre lobo");
        System.out.println("3. Cazador");

        int option = readInt(1, 3);

        System.out.print("Nombre del personaje: ");
        String name = scanner.nextLine();

        AbstractCharacter character;

        switch (option) {
            case 1 -> character = createVampire(name);
            case 2 -> character = createWerewolf(name);
            default -> character = createHunter(name);
        }

        // Assign basic skill
        character.skill = createBasicSkill(option);

        return character;
    }

    private Vampire createVampire(String name) {
        System.out.println("Creando vampiro...");
        return new Vampire(name, 5, 3, 200);
    }

    private Werewolf createWerewolf(String name) {
        System.out.println("Creando hombre lobo...");
        return new Werewolf(name, 5, 3);
    }

    private Hunter createHunter(String name) {
        System.out.println("Creando cazador...");
        return new Hunter(name, 5, 3);
    }

    /**
     * Creates a simple skill depending on type.
     */
    private SpecialSkill createBasicSkill(int type) {

        return switch (type) {
            case 1 -> new Discipline("Bite", 2, 1, 1);
            case 2 -> new Gift("Claws", 2, 1, 1);
            default -> new Talent("Aim", 2, 1);
        };
    }

    /**
     * Reads integer safely.
     */
    private int readInt(int min, int max) {
        int value;

        while (true) {
            try {
                System.out.print("Opción: ");
                value = Integer.parseInt(scanner.nextLine());

                if (value >= min && value <= max) {
                    return value;
                }

            } catch (Exception ignored) {}

            System.out.println("Valor inválido. Intenta de nuevo.");
        }
    }
}