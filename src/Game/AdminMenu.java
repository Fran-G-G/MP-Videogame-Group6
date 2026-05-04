package Game;


import DB.Singleton;

public class AdminMenu {

    private final Admin admin;

    public AdminMenu(Admin admin) {
        this.admin = admin;
    }

    public void show() {
        int option;
        boolean play = true;

        do {
            System.out.println("\n===== MENÚ ADMINISTRADOR =====");
            System.out.println("1. Cerrar Sesión | 2. Cancelar Cuenta | 3. Bloquear jugador | 4. Desbloquear jugador | 5. Editar personajes \n");
            option = ConsoleInput.readInt(1,5);

            switch (option) {
                case 1 -> play = logout();
                case 2 -> play = signOut(admin);
                case 3 -> blockUser();
                case 4 -> unblockUser();
                case 5 -> editCharacter();
                default -> System.out.println("Opción no válida.");
            }

        } while (option != 4 && play);

    }

    /**
     * LogOut
     */
    private boolean logout() {
        System.out.println("Cerrando sesión...");
        return false;
    }

    /**
     * SignOut
     */
    private boolean signOut(Admin admin) {
        Singleton singleton = Singleton.getInstance();

        boolean delete = ConsoleInput.readBoolean("¿Estás seguro de querer eliminar tu cuenta? ");

        if (delete){
            singleton.deleteAdmin(admin);
            return false;
        }else {
            return true;
        }
    }


    /**
     * Block player
     */
    private void blockUser() {
        String nick = ConsoleInput.readString("Introduce el nick del usuario a bloquear:");

        Player player = Singleton.getInstance().findPlayerByNick(nick);

        if (player == null) {
            System.out.println("No existe ningún jugador con ese nick.");
            return;
        }

        if (player.isBlocked()) {
            System.out.println("Ese jugador ya está bloqueado.");
            return;
        }

        player.block();
        Singleton.getInstance().updatePlayersDB();

        System.out.println("Jugador " + nick + " bloqueado correctamente.");
    }

    /**
     * Unlock player
     */
    private void unblockUser() {
        String nick = ConsoleInput.readString("Introduce el nick del usuario a desbloquear:");

        Player player = Singleton.getInstance().findPlayerByNick(nick);

        if (player == null) {
            System.out.println("No existe ningún jugador con ese nick.");
            return;
        }

        if (!player.isBlocked()) {
            System.out.println("Ese jugador no está bloqueado.");
            return;
        }

        player.unblock();
        Singleton.getInstance().updatePlayersDB();

        System.out.println("Jugador " + nick + " desbloqueado correctamente.");
    }

    /**
     * Edit character
     */
    private void editCharacter() {
        String nick = ConsoleInput.readString("Introduzca el nick del jugador cuyo personaje quiera editar: ");
        Player playerByNick = Singleton.getInstance().findPlayerByNick(nick);

        if (playerByNick == null) {
            System.out.println("Ese jugador no existe.");
            return;
        }

        if (playerByNick.getCharacter() == null) {
            System.out.println(playerByNick.getNick() + " no ha creado ningún personaje.");
            return;
        }

        Game.AbstractCharacter character = playerByNick.getCharacter();

        System.out.println("================================================================================");
        System.out.println("¿Qué quieres editar de su personaje? \n");

        System.out.println("1. Nombre | 2. Armas o armaduras | 3. Esbirros | 4. Fuerza | 5. Oro | 6. Habilidad especial | 7. Fortalezas o debilidades | 8. Cancelar");
        int option = ConsoleInput.readInt(1, 8);

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
            case 4 -> {
                System.out.println("Introduzca la nueva fuerza para el personaje: ");
                int power = ConsoleInput.readInt(1,5);
                character.setPower(power);
            }
            case 5 -> {
                System.out.println("Introduzca la cantidad de oro que quiera otorgarle al personaje: ");
                int gold = ConsoleInput.readInt(0,500);
                character.addGold(gold);
            }
            case 6 -> {
                SpecialSkillManager skillManager = new SpecialSkillManager();
                skillManager.manageSpecialSkills(character);
            }
            case 7 -> {
                System.out.println("¿Quiere modificar las debilidades, las fortalezas o ambas?");
                int selected = ConsoleInput.readInt(1,3);
                WeaknessManager weaknessManager = new WeaknessManager();
                StrengthManager strengthManager = new StrengthManager();
                switch (selected) {
                    case 1 -> weaknessManager.manageWeaknesses(character, false);
                    case 2 -> strengthManager.manageStrengths(character, false);
                    case 3 -> {
                        System.out.println("Primero las debilidades: ");
                        weaknessManager.manageWeaknesses(character, false);
                        System.out.println("\nY ahora las fortalezas: ");
                        strengthManager.manageStrengths(character, false);
                    }
                    default -> System.out.println("Opción no válida.");
                }
            }
            case 8 -> {
                return;
            }
            default -> System.out.println("Opción no válida.");
        }

        Singleton singleton = Singleton.getInstance();
        singleton.updatePlayersDB();

        System.out.println("\nFin del proceso de edición del personaje");
        System.out.println("================================================================================\n");

    }
}
