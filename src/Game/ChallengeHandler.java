package Game;

import DB.Singleton;

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

        // Comprobar que ambos tienen personaje
        if (challenger.getCharacter() == null || challenged.getCharacter() == null) {
            System.out.println("Ambos jugadores deben tener un personaje para desafiar.");
            return;
        }

        // Comprobar que ambos tienen equipo activo
        if (!challenger.getCharacter().hasActiveEquipment() ||
                !challenged.getCharacter().hasActiveEquipment()) {
            System.out.println("Ambos jugadores deben tener armas y armadura activas.");
            return;
        }

        System.out.println("================================================================================");
        System.out.println("Administrador, hay que validar un desafío");
        System.out.println("-----------------------------------------------------------------------------\n");
        String pass="";
        boolean cancel = false;
        while (!pass.equals("12345678") && !cancel) {
            pass= ConsoleInput.readString("Escribe la contraseña:");
            if (!pass.equals("12345678")) {
                System.out.println("Error en la contraseña");
                System.out.println("1. Reintentar | 2. Cancelar");
                cancel = (ConsoleInput.readInt(1, 2) == 2);
            }
        }

        if (cancel){
            System.out.println("Error al iniciar sesion");
            return;
        }

        Boolean validated = ConsoleInput.readBoolean("¿Deseas validar el desafio?(s/n)");

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

}