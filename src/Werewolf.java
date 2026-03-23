/**
 * Werewolf character.
 */
public class Werewolf extends AbstractCharacter {

    private int rage; // Range: 0 - 3

    public Werewolf(String name, int health, int power) {
        super(name, health, power);
        this.rage = 0;
    }

    public void increaseRage() {
        if (rage < 3) {
            rage++;
        }
    }

    public int getRage() {
        return rage;
    }
}