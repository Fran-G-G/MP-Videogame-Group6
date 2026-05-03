package Game;
import DB.Singleton;
import Game.GameStarter;

/**
 * Represents a challenge between two players.
 * Implements the Handler interface for the Chain of Responsibility pattern.
 */

public class ChallengeHandler {

    Calculator calculator= new Calculator();

    public void handler (Player challenger, Player challenged, Boolean op, Integer bet){

        if (!op){
            Integer penalty = calculator.calculateRejectionPenalty(bet);
            System.out.println("El retado ha rechazado el desafio, el retador sera recompensado con "+ penalty+ " monedas");
            challenged.getCharacter().addGold(-penalty);
            challenger.getCharacter().addGold(penalty);
            return;
        }



        // Check that both have an active equipment
        if (!challenger.getCharacter().hasActiveEquipment() ||
                !challenged.getCharacter().hasActiveEquipment()) {
            System.out.println("Ambos jugadores deben tener armas y armadura activas.");
            return;
        }

        System.out.println("================================================================================");
        System.out.println("Administrador, hay que validar un desafío");
        System.out.println("-----------------------------------------------------------------------------\n");
        Admin admin = logInAdmin();
        if (admin==null){
            System.out.println("Error al iniciar sesión del admnistrador, el desafío no ha sido validado");
            return;
        }

        Boolean validated = ConsoleInput.readBoolean("¿Deseas validar el desafio?");

        if (!validated){
            System.out.println("El administrador no ha validado este desafio");
            return;
        }

        System.out.println("================================================================================");
        System.out.println("Desafio validado, comenzando Combate");

        Challenge challenge= new Challenge(challenger,challenged,bet);

        CombatHandler combatHandler= new CombatHandler();
        combatHandler.handle(challenge);
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
}