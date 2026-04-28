import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Player user.
 */
public class Player extends User {


    private String registrationNumber;
    private AbstractCharacter character;

    private List<ChallengeHandler> challengesReceived;
    private List<ChallengeHandler> challengesSent;

    public Player(String name, String nick, String password) {
        super(name, nick, password);
        this.registrationNumber = generateRegistration();
        this.challengesReceived = new ArrayList<>();
        this.challengesSent = new ArrayList<>();
    }

    /**
     * Generates registration number in format LNNLL
     */
    private String generateRegistration() {
        Random r = new Random();
        return "" +
                (char) ('A' + r.nextInt(26)) +
                r.nextInt(10) +
                r.nextInt(10) +
                (char) ('A' + r.nextInt(26)) +
                (char) ('A' + r.nextInt(26));
    }

    public String getRegistrationNumber(){
        return this.registrationNumber;
    }

    public void setCharacter(AbstractCharacter character) {
        this.character = character;
    }

    public AbstractCharacter getCharacter() {
        return character;
    }

    public void addReceivedChallenge(ChallengeHandler c) {
        challengesReceived.add(c);
    }

    public void addSentChallenge(ChallengeHandler c) {
        challengesSent.add(c);
    }

    public List<ChallengeHandler> getChallengesReceived() {
        return challengesReceived;
    }
}