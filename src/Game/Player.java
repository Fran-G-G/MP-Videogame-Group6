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

    /** Complete history of all combats (accepted challenges and rejections). */
    private List<CombatRecord> combatHistory;

    public Player(String name, String nick, String password) {
        super(name, nick, password);
        this.registrationNumber = generateRegistration();
        this.combatHistory = new ArrayList<>();
    }

    public Player(String name, String nick, String password, String registrationNumber) {
        super(name, nick, password);
        this.registrationNumber = registrationNumber;
        this.combatHistory = new ArrayList<>();
    }

    /**
     * Generates registration number in format LNNLL.
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

    public String getRegistrationNumber() {
        return this.registrationNumber;
    }

    public void setCharacter(AbstractCharacter character) {
        this.character = character;
    }

    public AbstractCharacter getCharacter() {
        return character;
    }

    /**
     * Registers a new combat record in the player's history.
     */
    public void addCombatRecord(CombatRecord record) {
        combatHistory.add(record);
    }

    /**
     * Returns the complete list of combat records.
     */
    public List<CombatRecord> getCombatHistory() {
        return combatHistory;
    }

    public boolean is_blocked() {
        return is_blocked;
    }

    public void blocked() {
        is_blocked = true;
    }

    public void unblocked() {
        is_blocked = false;
    }

    /**
     * Removes the player's character (used when deleting character).
     */
    public void deleteCharacter() {
        character = null;
    }
}