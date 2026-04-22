/**
 * Handles the combat phase of a challenge.
 * This is the final handler in the chain.
 */
public class CombatHandler extends ChallengeHandler {

    public CombatHandler(Player challenger, Player challenged) {
        this.player1 = challenger;
        this.player2 = challenged;
    }

    @Override
    public void handle(ChallengeHandler challenge) {
        System.out.println("\n--- Fase de Combate ---");

        // Ensure both players have characters with active equipment
        if (player1.getCharacter() == null || player2.getCharacter() == null) {
            System.out.println("Error: Ambos jugadores deben tener un personaje con equipo activo para luchar.");
            return;
        }

        // Create and start the combat mediator
        CombatMediator mediator = new CombatMediator(player1, player2);
        mediator.start();

        // No next handler; combat is the end of the chain
    }
}