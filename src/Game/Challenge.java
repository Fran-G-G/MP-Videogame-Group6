package Game;

import java.io.Serializable;

public class Challenge implements Serializable {
    private Player challenger;
    private Player challenged;
    private int goldBet;
    private Status status;
    private boolean validated;

    public Challenge(Player var1, Player var2, int goldBet) {
        if (goldBet < 0) {
            throw new IllegalArgumentException("La apuesta no puede ser negativa");
        } else {
            this.challenger = var1;
            this.challenged = var2;
            this.goldBet = goldBet;
            this.status = Challenge.Status.PENDING;
            this.validated = false;
        }
    }

    public boolean isValidated() {
        return this.validated;
    }

    public Status getStatus() {
        return this.status;
    }

    public Player getChallenger() {
        return this.challenger;
    }

    public Player getChallenged() {
        return this.challenged;
    }

    public int getGoldBet() {
        return this.goldBet;
    }

    public void accept() {
        if (!this.validated) {
            throw new IllegalStateException("El desafío no ha sido validado");
        } else {
            this.status = Challenge.Status.ACCEPTED;
        }
    }

    public void reject() {
        this.status = Challenge.Status.REJECTED;
        int var1 = (int) ((double) this.goldBet * 0.1);
        System.out.println("Penalización: pierdes " + var1 + " de oro.");
    }

    public static enum Status {
        PENDING,
        ACCEPTED,
        REJECTED;
    }
}
