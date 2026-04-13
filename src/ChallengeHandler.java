/**
 * Represents a challenge between two players.
 */
public abstract class ChallengeHandler {

    protected ChallengeHandler next;
    protected Character player1;
    protected Character player2;

    public ChallengeHandler setNext(ChallengeHandler next) {
        this.next = next;
        return next;
    }

    public abstract void handle(ChallengeHandler challenge);
}