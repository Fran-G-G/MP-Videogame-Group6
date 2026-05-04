package Game;

/**
 * Handles the combat phase of a challenge.
 * This is the final handler in the chain.
 */
public class CombatHandler {
    protected Player player1;
    protected Player player2;

    /**
     * Executes the combat and returns the record.
     *
     * @param challenge the challenge containing the players and bet
     * @return combat record with all details
     */
    public CombatRecord handle(Challenge challenge) {
        player1 = challenge.getChallenger();
        player2 = challenge.getChallenged();

        System.out.println("\n--- Fase de Combate ---");

        if (player1.getCharacter() == null || player2.getCharacter() == null) {
            System.out.println("Error: Ambos jugadores deben tener un personaje con equipo activo para luchar.");
            return null;
        }

        CombatMediator mediator = new CombatMediator(player1, player2);
        CombatRecord record = mediator.start(challenge.getGoldBet());

        // Transfer gold
        if (record.getWinnerNick().equals(player1.getNick())) {
            player1.getCharacter().addGold(challenge.getGoldBet());
            player2.getCharacter().addGold(-challenge.getGoldBet());
        } else {
            player2.getCharacter().addGold(challenge.getGoldBet());
            player1.getCharacter().addGold(-challenge.getGoldBet());
        }

        // Add record to both players
        player1.addCombatRecord(record);
        player2.addCombatRecord(record);

        return record;
    }
}