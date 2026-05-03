package Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Player user.
 */
public class Player extends User implements Serializable {

    private String registrationNumber;
    private AbstractCharacter character;
    private boolean is_blocked;
    private List<Integer> goldList;

    private List<String> challenges;

    public Player(String name, String nick, String password) {
        super(name, nick, password);
        this.registrationNumber = generateRegistration();
        this.challenges = new ArrayList<>();
    }

    public Player(String name, String nick, String password, String registrationNumber) {
        super(name, nick, password);
        this.registrationNumber = registrationNumber;
        this.challenges = new ArrayList<>();
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

    public void addChallenge(String other) {
        challenges.add(other);
    }

    public List<String> getChallenges() {
        return challenges;
    }


    public void addGoldList (Integer bet) { goldList.add(bet); }

    public List<Integer> getGoldList() {return goldList; }

    public boolean isIs_blocked(){return is_blocked;}

    public void blocked(){
        is_blocked=true;
    }

    public void unblocked(){
        is_blocked=false;
    }
}