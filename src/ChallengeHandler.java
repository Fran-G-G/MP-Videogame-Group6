/**
 * Represents a challenge between two players.
 * Implements the Handler interface for the Chain of Responsibility pattern.
 */
public class ChallengeHandler {

    public void handler (Player challenger){

        String targetNick = ConsoleInput.readString("Introduce el nick del jugador al que quieres desafiar:");

        //Player challenged = DBManager.getInstance().findPlayerByNick(targetNick);
        Player challenged= new Player("aleatorio", "aleatorio1", "1234567889");

        if (challenged == null) {
            System.out.println("No existe ningún jugador con ese nick.");
            return;
        }

        // 3. Evitar que se desafíe a sí mismo
        if (challenged.equals(challenger)) {
            System.out.println("No puedes desafiarte a ti mismo.");
            return;
        }

        System.out.println("================================================================================\n");
        // AQUI VA EL PLAYER2 INICIANDO SESIÓN


        // 4. Comprobar que ambos tienen personaje
        if (challenger.getCharacter() == null || challenged.getCharacter() == null) {
            System.out.println("Ambos jugadores deben tener un personaje para desafiar.");
            return;
        }

        // 5. Comprobar que ambos tienen equipo activo
        if (!challenger.getCharacter().hasActiveEquipment() ||
                !challenged.getCharacter().hasActiveEquipment()) {
            System.out.println("Ambos jugadores deben tener armas y armadura activas.");
            return;
        }

        // 6. Pedir apuesta
        System.out.println("¿Cuánto oro quieres apostar?");
        int betGold = ConsoleInput.readInt(0,challenger.getCharacter().getGold());

        // Create and validate challenge
        Challenge challenge = new Challenge(challenger, challenged, betGold);

        CombatHandler combatHandler = new CombatHandler();

        System.out.println("\nDesafío aceptado. ¡Empieza el combate!\n");

        // Iniciar el proceso
        combatHandler.handle(challenge);

    }

}