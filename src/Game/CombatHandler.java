package Game;

/**
 * Handles the combat phase of a challenge.
 * This is the final handler in the chain.
 */
public class CombatHandler {
    protected Player player1;
    protected Player player2;



    public void handle(Challenge challenge) {

        player1= challenge.getChallenger();
        player2= challenge.getChallenged();

        System.out.println("\n--- Fase de Combate ---");

        // Ensure both players have characters with active equipment X2
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