/**
 * Werewolf character.
 */
public class Werewolf extends AbstractCharacter {

    private int rage; // Range: 0 - 3
    private boolean transformed; // Human = false, Wolf = true
    private int height;
    private int weight;

    public Werewolf(String name, int health, int power, int height, int weight) {
        super(name, health, power);
        this.rage = 0;
        this.transformed = false;
        this.height = height;
        this.weight = weight;
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