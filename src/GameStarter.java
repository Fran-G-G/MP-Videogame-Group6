/**
 * Initializes and runs the game in terminal mode.
 */
public class GameStarter {

    public void run() {

        System.out.println("Bienvenido al juego.\n");
        int option;

        System.out.println("1. Registrarse | 2. Iniciar Sesión");
        option = ConsoleInput.readInt(1, 2);

        switch (option) {
            case 1 -> signIn(); // Registrarse
            case 2 -> logIn(); // Iniciar Sesión
        }

        boolean play = true;
        Player p = createPlayer(0); // Habrá que quitarlo, es solo para que no salten errores.

        while (play) {
            System.out.println("================================================================================\n");
            System.out.println("MENU PRINCIPAL");
            System.out.println("1. Cerrar Sesión | 2. Cancelar Cuenta | 3. Crear nuevo personaje | 4. Editar personaje | 5. Desafiar | 6. Consultar ranking \n");
            option = ConsoleInput.readInt(1, 6);

            switch (option) {
                case 1 -> play = logOut();
                case 2 -> signOut();
                case 3 -> createCharacter(p);
                case 4 -> editCharacter();
                case 5 -> challenge();
                case 6 -> seeRanking();
            }
        }

        System.out.println("\nHasta pronto :)\n");



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

        String name = ConsoleInput.readString("Nombre: ");

        String nick = ConsoleInput.readString("Nick: ");

        String pass = ConsoleInput.readString("Password (8-12 chars): ");

        return new Player(name, nick, pass);
    }

    private void signIn() {

    }

    private void logIn() {

    }

    private boolean logOut() {
        System.out.println("Cerrando sesión...");
        return false;
    }

    private void signOut() {

    }

    /**
     * Creates a character for a player.
     */
    private AbstractCharacter createCharacter(Player player) {

        System.out.println("================================================================================");
        System.out.println("Comenzamos con la creación del personaje \n");

        // Choose the character type between the given options.
        System.out.println(player.getNick() + ", elige el tipo de personaje que quieras crear:");
        System.out.println("1. Vampiro | 2. Hombre lobo | 3. Cazador");
        int option = ConsoleInput.readInt(1, 3);

        // Start the process of creating a new character.
        CharacterFactory characterFactory;
        AbstractCharacter character;
        switch (option) {
            case 1 -> { characterFactory = new VampireFactory(); character = characterFactory.createProduct(); }
            case 2 -> { characterFactory = new WerewolfFactory(); character = characterFactory.createProduct(); }
            default -> { characterFactory = new HunterFactory(); character = characterFactory.createProduct(); }
        }

        System.out.println("\nFin del proceso de creación del personaje\n");

        return character;
    }

    private void editCharacter() {

    }

    private void challenge() {

    }

    private void seeRanking() {

    }
}