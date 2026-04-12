import java.util.Random;
import java.util.Scanner;

/**
 * Factory class that helps with the creation of ghoul-type minions.
 */
public class GhoulFactory extends MinionFactory {

    private final AbstractCharacter characterOwner; // Reference to the character
    private Demon minionOwner; // Reference to the minion

    public GhoulFactory(AbstractCharacter characterOwner, Demon minionOwner) {
        this.characterOwner = characterOwner;
        this.minionOwner = minionOwner;
    }

    @Override
    public void createProduct() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Ponle un nombre a tu esbirro ghoul: ");
        String name = scanner.nextLine();

        int health = random.nextInt(3) + 1;
        int dependency = random.nextInt(5) + 1;

        Ghoul ghoul = new Ghoul(name, health, dependency);

        if (minionOwner == null) {
            this.characterOwner.addMinion(ghoul);
            System.out.println(name + " añadido como esbirro de " + this.characterOwner.getName() + " con éxito.");
        } else {
            this.minionOwner.addMinion(ghoul);
            System.out.println(name + " añadido como esbirro del esbirro " + this.minionOwner.getName() + " con éxito.");
        }
    }

    public void setMinionOwner(Demon minion) {
        this.minionOwner = minion;
    }
}
