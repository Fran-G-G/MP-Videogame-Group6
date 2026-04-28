package Game;

import DB.Singleton;
/**
 * Initializes and runs the game in terminal mode.
 */
public class GameStarter {

    public void run() {
        int option;

        Player p = null;
        while (p == null) {
            System.out.println("1. Registrarse | 2. Iniciar Sesión");
            option = ConsoleInput.readInt(1, 2);

            switch (option) {
                case 1 -> p = signIn(); // Registrarse
                case 2 -> p = logIn(); // Iniciar Sesión
            }
        }

        boolean play = true;
//        Player p = createPlayer(0); // Habrá que quitarlo, es solo para que no salten errores.
        AbstractCharacter character = null;

        while (play) {
            System.out.println("================================================================================\n");
            System.out.println("MENU PRINCIPAL");
            System.out.println("1. Cerrar Sesión | 2. Cancelar Cuenta | 3. Crear nuevo personaje | 4. Editar personaje | 5. Desafiar | 6. Consultar ranking \n");
            option = ConsoleInput.readInt(1, 6);

            switch (option) {
                case 1 -> play = logOut();
                case 2 -> signOut();
                case 3 -> character = createCharacter(p);
                case 4 -> {
                    if (character != null) {
                        editCharacter(character);
                    }
                }
                case 5 -> challenge();
                case 6 -> seeRanking();
            }
        }

        System.out.println("\nHasta pronto :)\n");
    }

    private Player signIn() {
        Singleton singleton = Singleton.getInstance();

        System.out.println("Nuevo Jugador: ");
        String name = ConsoleInput.readString("Nombre: ");
        String nick = ConsoleInput.readString("Nick: ");
        String pass = ConsoleInput.readString("Password (8-12 chars): ");

        Player player = new Player(name, nick, pass);
        singleton.registerUser(name, nick, pass, player.getRegistrationNumber());
        return player;
    }

    private Player logIn() {
        Singleton singleton = Singleton.getInstance();
        boolean cancel = false;

        Player player = null;
        while (player == null && !cancel) {
            System.out.println("Introduce tus datos: ");
            String nick = ConsoleInput.readString("Nick: ");
            String pass = ConsoleInput.readString("Password (8-12 chars): ");
            if (singleton.checkUser(nick, pass)) {
                player = new Player("name", nick, pass);
                System.out.println("Funciona ");
            } else {
                System.out.println("Error en el nick o contraseña");
                System.out.println("1. Reintentar | 2. Cancelar");
                cancel = (ConsoleInput.readInt(1, 2) == 2);
            }
        }

        return player;
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
        AbstractCharacter character = null;
        switch (option) {
            case 1 -> { characterFactory = new VampireFactory(); character = characterFactory.createProduct(); }
            case 2 -> { characterFactory = new WerewolfFactory(); character = characterFactory.createProduct(); }
            case 3 -> { characterFactory = new HunterFactory(); character = characterFactory.createProduct(); }
        }

        System.out.println("\nFin del proceso de creación del personaje\n");

        Singleton singleton = Singleton.getInstance();
        singleton.registerCharacter(character);
        return character;
    }

    private void editCharacter(AbstractCharacter character) {
        System.out.println("================================================================================");
        System.out.println("¿Qué quieres editar de tu personaje? \n");

        System.out.println("1. Nombre | 2. Armas o armaduras | 3. Esbirros");
        int option = ConsoleInput.readInt(1, 3);

        // Start the process of editing a character characteristic.
        switch (option) {
          case 1 -> {
              String newName = ConsoleInput.readString("Introduzca el nuevo nombre para " + character.getName() + ": ");
              character.setName(newName);
              System.out.println("Nuevo nombre del personaje modificado correctamente.");
          }
          case 2 -> {
              EquipmentManager eqManager = new EquipmentManager();
              eqManager.manageEquipment(character);
          }
          //case 3 -> character.
        }

        System.out.println("\nFin del proceso de edición del personaje\n");
    }

    private void challenge() {
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
        /*
        CombatHandler combatHandler = new CombatHandler(p1, p2);
        combatHandler.handle(null);
        */
    }

    private void seeRanking() {
        Ranking ranking = new Ranking();
        ranking.showRanking();
    }
}