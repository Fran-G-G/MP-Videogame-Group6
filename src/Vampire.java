/**
 * Vampire character.
 */
public class Vampire extends AbstractCharacter {

    private int blood; // Range: 0 - 10
    private int age;

    public Vampire(String name, int health, int power, int age) {
        super(name, health, power);
        this.age = age;
        this.blood = 10;
    }

    public boolean useDiscipline() {
        if (!(skill instanceof Discipline d)) return false;

        if (blood >= d.getBloodCost()) {
            blood -= d.getBloodCost();
            return true;
        }

        return false;
    }

    public void recoverBlood() {
        blood = Math.min(10, blood + 4);
    }

    public int getBloodPoints() {
        return blood;
    }

}