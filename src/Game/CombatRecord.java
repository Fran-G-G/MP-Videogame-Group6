package Game;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Immutable record of a completed combat.
 * Stores all information required by the specification.
 */
public class CombatRecord implements Serializable {

    private final String challengerNick;
    private final String challengedNick;
    private final int roundsPlayed;
    private final Date combatDate;
    private final String winnerNick;
    private final List<String> playersWithMinionsAlive;
    private final int goldBet;

    public CombatRecord(String challengerNick, String challengedNick, int roundsPlayed,
                        Date combatDate, String winnerNick,
                        List<String> playersWithMinionsAlive, int goldBet) {
        this.challengerNick = challengerNick;
        this.challengedNick = challengedNick;
        this.roundsPlayed = roundsPlayed;
        this.combatDate = combatDate;
        this.winnerNick = winnerNick;
        this.playersWithMinionsAlive = playersWithMinionsAlive;
        this.goldBet = goldBet;
    }

    public String getChallengerNick() { return challengerNick; }
    public String getChallengedNick() { return challengedNick; }
    public int getRoundsPlayed() { return roundsPlayed; }
    public Date getCombatDate() { return combatDate; }
    public String getWinnerNick() { return winnerNick; }
    public List<String> getPlayersWithMinionsAlive() { return playersWithMinionsAlive; }
    public int getGoldBet() { return goldBet; }

    @Override
    public String toString() {
        return String.format(
                "%s desafió a %s | Rondas: %d | Fecha: %s | Ganador: %s | Esbirros vivos: %s | Oro apostado: %d",
                challengerNick, challengedNick, roundsPlayed, combatDate,
                winnerNick, playersWithMinionsAlive, goldBet
        );
    }
}