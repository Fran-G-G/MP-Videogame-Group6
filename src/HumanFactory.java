import java.util.Random;
import java.util.Scanner;

/**
 * Factory class that helps with the creation of human-type minions.
 */
public class HumanFactory extends MinionFactory {

    private final AbstractCharacter characterOwner; // Reference to the character
    private Demon minionOwner; // Reference to the minion

    public HumanFactory(AbstractCharacter characterOwner, Demon minionOwner) {
        this.characterOwner = characterOwner;
        this.minionOwner = minionOwner;
    }

    @Override
    public void createProduct() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Ponle un nombre a tu esbirro humano: ");
        String name = scanner.nextLine();

        int health = random.nextInt(3) + 1;
        Human.Loyalty loyalty = Human.Loyalty.LOW;

        Human human = new Human(name, health, loyalty);

        if (minionOwner == null) {
            this.characterOwner.addMinion(human);
            System.out.println(name + " añadido como esbirro de " + this.characterOwner.getName() + " con éxito.");
        } else {
            this.minionOwner.addMinion(human);
            System.out.println(name + " añadido como esbirro del esbirro " + this.minionOwner.getName() + " con éxito.");
        }
    }

    public void setMinionOwner(Demon minion) {
        this.minionOwner = minion;
    }
}
