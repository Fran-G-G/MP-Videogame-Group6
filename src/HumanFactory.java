import java.util.Random;

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
    public Human createProduct() {
        Random random = new Random();

        String name = ConsoleInput.readString("Ponle un nombre a tu esbirro humano: ");

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

        return human;
    }

    public void setMinionOwner(Demon minion) {
        this.minionOwner = minion;
    }
}
