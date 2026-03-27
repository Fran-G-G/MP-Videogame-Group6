/**
 * Special skill used by Werewolves.
 */
public class Gift extends SpecialSkill {

    private final int minRage;

    public Gift(String name, int attack, int defense, int minRage) {
        super(name, attack, defense);
        this.minRage = minRage;
    }

    public int getMinRage() {
        return minRage;
    }
}