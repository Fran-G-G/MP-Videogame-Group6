/**
 * Admin user.
 */
public class Admin extends User {

    public Admin(String name, String nick, String password) {
        super(name, nick, password);
    }

    /**
     * Validates a challenge (simplified version)
     */
    public void validateChallenge(Challenge challenge) {
        challenge.setValidated(true);
    }
}