/**
 * Hunter character.
 */
public class Hunter extends AbstractCharacter {

    private int willpower; // Range: 0 - 3

    public Hunter(String name, int health, int power) {
        super(name, health, power);
        this.willpower = 3;
    }

    public void decreaseWillpower() {
        if (willpower > 0) {
            willpower--;
        }
    }

    public int getWillpower() {
        return willpower;
    }
}