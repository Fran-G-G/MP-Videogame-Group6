package Game;


import DB.Singleton;

public class AdminMenu {

    private final Admin admin;

    public AdminMenu(Admin admin) {
        this.admin = admin;
    }

    public void show() {
        int option;
        boolean play=true;

        do {
            System.out.println("\n===== MENÚ ADMINISTRADOR =====");
            System.out.println("1. Bloquear jugador");
            System.out.println("2. Desbloquear jugador");
            System.out.println("3. Cerrar sesión");
            System.out.print("Elige una opción: ");

            option = ConsoleInput.readInt(1,3);

            switch (option) {
                case 1 -> blockUser();
                case 2 -> unblockUser();
                case 3 -> play= logout();
                default -> System.out.println("Opción no válida.");
            }

        } while (option != 4 && play);

    }

    // ============================
    // BLOQUEAR USUARIO
    // ============================

    private void blockUser() {
        String nick = ConsoleInput.readString("Introduce el nick del usuario a bloquear:");

        Player player = Singleton.getInstance().findPlayerByNick(nick);

        if (player == null) {
            System.out.println("No existe ningún jugador con ese nick.");
            return;
        }

        if (player.isIs_blocked()) {
            System.out.println("Ese jugador ya está bloqueado.");
            return;
        }

        player.blocked();
        Singleton.getInstance().updatePlayersDB();

        System.out.println("Jugador " + nick + " bloqueado correctamente.");
    }

    // ============================
    // DESBLOQUEAR USUARIO
    // ============================

    private void unblockUser() {
        String nick = ConsoleInput.readString("Introduce el nick del usuario a desbloquear:");

        Player player = Singleton.getInstance().findPlayerByNick(nick);

        if (player == null) {
            System.out.println("No existe ningún jugador con ese nick.");
            return;
        }

        if (!player.isIs_blocked()) {
            System.out.println("Ese jugador no está bloqueado.");
            return;
        }

        player.unblocked();
        Singleton.getInstance().updatePlayersDB();

        System.out.println("Jugador " + nick + " desbloqueado correctamente.");
    }


    // ============================
    // LOGOUT
    // ============================

    private boolean logout() {
        System.out.println("Cerrando sesión...");
        return false;
    }
}
