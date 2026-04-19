/**
 * Base interface for the Chain of Responsibility pattern.
 * All handlers in the challenge flow must implement this.
 */
public interface Handler {
    Handler setNext(Handler next);
    void handle(ChallengeHandler challenge);
}