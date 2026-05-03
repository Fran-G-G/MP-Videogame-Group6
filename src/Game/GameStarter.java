package Game;

import DB.Singleton;
/**
 * Initializes and runs the game in terminal mode.
 */
public class GameStarter {

    public void run() {
        int option;

        Player p = null;

        Admin a=null;
        while (p == null && a==null) {
            System.out.println("1. Registrarse | 2. Iniciar Sesión | 3. Iniciar como administrador");
            option = ConsoleInput.readInt(1, 3);

            switch (option) {
                case 1 -> p = signIn(); // Registrarse
                case 2 -> p = logIn(); // Iniciar Sesión
                case 3 -> a= logInAdmin();
            }
        }



        boolean play = true;
        if (a != null) {
            AdminMenu menu = new AdminMenu(a);
            menu.show();
            play=false;
        }

        AbstractCharacter character = null;

        while (play) {
            System.out.println("================================================================================\n");
            System.out.println("MENU PRINCIPAL");
            System.out.println("1. Cerrar Sesión | 2. Cancelar Cuenta | 3. Crear nuevo personaje | 4. Editar personaje | 5. Desafiar | 6. Consultar ranking \n");
            option = ConsoleInput.readInt(1, 6);

            switch (option) {
                case 1 -> play = logOut();
                case 2 -> play = signOut(p);
                case 3 -> character = createCharacter(p);
                case 4 -> {
                    if (character != null) {
                        editCharacter(character);
                    } else {
                        System.out.println("Todavía no has creado ningún personaje");
                    }
                }
                case 5 -> challenge(p);
                case 6 -> seeRanking();
            }
        }

        System.out.println("\nHasta pronto :)\n");
    }

    public Admin logInAdmin() {
        Singleton singleton = Singleton.getInstance();
        boolean cancel = false;

        Admin admin = null;
        while (admin == null && !cancel) {
            System.out.println("Introduce tus datos: ");
            String nick = ConsoleInput.readString("Nick: ");
            String pass = ConsoleInput.readString("Password (8-12 chars): ");
            admin = singleton.loadAdmin(nick, pass);
            if (admin == null) {
                System.out.println("Error en el nick o contraseña");
                System.out.println("1. Reintentar | 2. Cancelar");
                cancel = (ConsoleInput.readInt(1, 2) == 2);
            }
        }

        return admin;
    }


    private Player signIn() {
        Singleton singleton = Singleton.getInstance();

        System.out.println("Nuevo Jugador: ");
        String name = ConsoleInput.readString("Nombre: ");
        String nick = ConsoleInput.readString("Nick: ");
        String pass = ConsoleInput.readString("Password (8-12 chars): ");

        Player player = new Player(name, nick, pass);
        if (singleton.findPlayerByNick(nick) == null){
            singleton.registerPlayer(player);
            return player;
        }else {
            System.out.println("Ese jugador ya existe, elige otro nick o inicia sesión");
            return null;
        }
    }

    public Player logIn() {
        Singleton singleton = Singleton.getInstance();
        boolean cancel = false;

        Player player = null;
        while (player == null && !cancel) {
            System.out.println("Introduce tus datos: ");
            String nick = ConsoleInput.readString("Nick: ");
            String pass = ConsoleInput.readString("Password (8-12 chars): ");
            player = singleton.loadPlayer(nick, pass);
            if (player == null) {
                System.out.println("Error en el nick o contraseña");
                System.out.println("1. Reintentar | 2. Cancelar");
                cancel = (ConsoleInput.readInt(1, 2) == 2);
            }
        }
        if (player !=null && player.isIs_blocked()){
            System.out.println("Jugador bloqueado, no puede iniciar sesión");
            player=null;
        }

        return player;
    }

    private boolean logOut() {
        System.out.println("Cerrando sesión...");
        return false;
    }

    private boolean signOut(Player player) {
        Singleton singleton = Singleton.getInstance();

        boolean delete = ConsoleInput.readBoolean("¿Estás seguro de querer eliminar tu cuenta? ");

        if (delete){
            singleton.deletePlayer(player);
            return false;
        }else {
            return true;
        }
    }


    /**
     * Creates a character for a player.
     */
    private AbstractCharacter createCharacter(Player player) {
        if (player.getCharacter() != null){
            System.out.println("Ya hay un personaje creado \n");
            return player.getCharacter();
        } else {
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
                case 1 -> {
                    characterFactory = new VampireFactory();
                    character = characterFactory.createProduct();
                }
                case 2 -> {
                    characterFactory = new WerewolfFactory();
                    character = characterFactory.createProduct();
                }
                case 3 -> {
                    characterFactory = new HunterFactory();
                    character = characterFactory.createProduct();
                }
            }

            player.setCharacter(character);
            System.out.println("\nFin del proceso de creación del personaje\n");

            Singleton singleton = Singleton.getInstance();
            singleton.updatePlayersDB();
            return character;
        }
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
            case 3 -> {
                MinionManager minionManager = new MinionManager();
                minionManager.editMinions(character);
            }
        }

        Singleton singleton = Singleton.getInstance();
        singleton.updatePlayersDB();
        System.out.println("\nFin del proceso de edición del personaje\n");
    }

    private void challenge(Player challenger) {
        ChallengeHandler challengeHandler= new ChallengeHandler();
        // Ask for the target player's nickname

        String nick = ConsoleInput.readString("¿A qué jugador quieres desafiar?");

        Player challenged = Singleton.getInstance().findPlayerByNick(nick);

        if (challenged == null) {
            System.out.println("Ese jugador no existe.");
            return;
        }

        if (challenged.equals(challenger)) {
            System.out.println("No puedes desafiarte a ti mismo.");
            return;
        }

        if (challenger.getCharacter() == null || challenged.getCharacter() == null) {
            System.out.println("Ambos jugadores deben tener un personaje para desafiar.");
            return;
        }

        // Ask for the bet
        System.out.println("¿Cuánto oro quieres apostar?");
        int bet = ConsoleInput.readInt(0,challenger.getCharacter().getGold());

        if (bet <= 0 || bet > challenger.getCharacter().getGold()) {
            System.out.println("Apuesta inválida.");
            return;
        }

        System.out.println("================================================================================");
        System.out.println("Jugador "+challenged.getNick()+ " has sido desafiado: Escriba su contraseña para aceptar o rechazar el desafío");
        System.out.println("-----------------------------------------------------------------------------\n");


        challenged= logIn();

        if (challenged == null){
            System.out.println("Error del desafiado al iniciar sesión");
            return;
        }

        System.out.println("-----------------------------------------------------------------------------\n");
        Boolean op = ConsoleInput.readBoolean("¿Deseas aceptar el desafio?");

        challengeHandler.handler(challenger,challenged, op, bet);

        Singleton singleton = Singleton.getInstance();
        singleton.updatePlayersDB();
    }

    private void seeRanking() {
        Ranking ranking = new Ranking();
        ranking.showRanking();
    }
}