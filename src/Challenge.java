/**
 * Represents a challenge between two players.
 */
public class Challenge {

    public enum Status {
        PENDING, ACCEPTED, REJECTED
    }

    private Player challenger;
    private Player challenged;

    private int goldBet;
    private Status status;
    private boolean validated;

    public Challenge(Player challenger, Player challenged, int goldBet) {

        if (goldBet < 0) {
            throw new IllegalArgumentException("La apuesta no puede ser negativa");
        }

        this.challenger = challenger;
        this.challenged = challenged;
        this.goldBet = goldBet;
        this.status = Status.PENDING;
        this.validated = false;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public boolean isValidated() {
        return validated;
    }

    public Status getStatus() {
        return status;
    }

    public Player getChallenger() {
        return challenger;
    }

    public Player getChallenged() {
        return challenged;
    }

    public int getGoldBet() {
        return goldBet;
    }

    public void accept() {
        if (!validated) {
            throw new IllegalStateException("El desafío no ha sido validado");
        }
        status = Status.ACCEPTED;
    }

    public void reject() {
        status = Status.REJECTED;

        // Penalización 10%
        int penalty = (int) (goldBet * 0.1);
        System.out.println("Penalización: pierdes " + penalty + " de oro.");
    }
}