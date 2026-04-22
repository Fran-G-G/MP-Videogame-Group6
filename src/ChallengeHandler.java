/**
 * Represents a challenge between two players.
 * Implements the Handler interface for the Chain of Responsibility pattern.
 */
public abstract class ChallengeHandler implements Handler {

    protected ChallengeHandler next;
    protected Player player1;
    protected Player player2;
    protected boolean validated;

    @Override
    public ChallengeHandler setNext(Handler next) {
        if (next instanceof ChallengeHandler) {
            this.next = (ChallengeHandler) next;
        } else {
            throw new IllegalArgumentException("Next handler must be a ChallengeHandler");
        }
        return this.next;
    }

    @Override
    public abstract void handle(ChallengeHandler challenge);

    public void setValidated(boolean value) {
        validated = value;
    }

    public boolean isValidated() {
        return validated;
    }
}