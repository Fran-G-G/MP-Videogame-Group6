package Game;
import DB.Singleton;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a challenge between two players.
 * Implements the Handler interface for the Chain of Responsibility pattern.
 */
public class ChallengeHandler {

    Calculator calculator = new Calculator();

    /**
     * Handles the result of a challenge (accept or reject).
     *
     * @param challenger player who started the challenge
     * @param challenged player who received the challenge
     * @param accepted   true if the challenge was accepted
     * @param bet        the amount of gold bet
     */
    public void handler(Player challenger, Player challenged, Boolean accepted, Integer bet) {
        if (!accepted) {
            // Challenge rejected: apply 10% penalty to the challenged player
            int penalty = calculator.calculateRejectionPenalty(bet);
            System.out.println("El retado ha rechazado el desafío. Penalización de " + penalty + " monedas.");

            challenged.getCharacter().addGold(-penalty);
            challenger.getCharacter().addGold(penalty);

            // Register the rejected challenge as a combat record with 0 rounds and no winner
            CombatRecord rejectRecord = new CombatRecord(
                    challenger.getNick(),
                    challenged.getNick(),
                    0,
                    new Date(),
                    "N/A (rechazado)",
                    new ArrayList<>(),   // no minions alive data
                    penalty
            );
            challenger.addCombatRecord(rejectRecord);
            challenged.addCombatRecord(rejectRecord);

            return;
        }

        // Challenge accepted: validate and start combat
        if (!challenger.getCharacter().hasActiveEquipment() ||
                !challenged.getCharacter().hasActiveEquipment()) {
            System.out.println("Ambos jugadores deben tener armas y armadura activas.");
            return;
        }

        System.out.println("================================================================================");
        System.out.println("Administrador, hay que validar un desafío");
        System.out.println("-----------------------------------------------------------------------------\n");
        Admin admin = logInAdmin();
        if (admin == null) {
            System.out.println("Error al iniciar sesión del administrador, el desafío no ha sido validado");
            return;
        }

        Boolean validated = ConsoleInput.readBoolean("¿Deseas validar el desafío?");
        if (!validated) {
            System.out.println("El administrador no ha validado este desafío");
            return;
        }

        System.out.println("================================================================================");
        System.out.println("Desafío validado, comenzando Combate");

        Challenge challenge = new Challenge(challenger, challenged, bet);

        CombatHandler combatHandler = new CombatHandler();
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