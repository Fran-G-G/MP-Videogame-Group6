/**
 * Represents a challenge between two players.
 */
public abstract class ChallengeHandler {

    protected ChallengeHandler next;

    public ChallengeHandler setNext(ChallengeHandler next) {
        this.next = next;
        return next;
    }

    public abstract void handle(ChallengeHandler challenge);
}