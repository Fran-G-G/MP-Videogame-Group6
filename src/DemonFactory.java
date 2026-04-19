import java.util.Random;

/**
 * Factory class that helps with the creation of demon-type minions.
 */
public class DemonFactory extends MinionFactory {

    private final AbstractCharacter characterOwner; // Reference to the character
    private Demon minionOwner; // Reference to the minion
    private int depth; // To control the number of minions that are minions of other minions
    private final GhoulFactory ghoulFactory;
    private final HumanFactory humanFactory;

    public DemonFactory(AbstractCharacter characterOwner, Demon minionOwner, GhoulFactory ghoulFactory, HumanFactory humanFactory) {
        this.characterOwner = characterOwner;
        this.minionOwner = minionOwner;
        this.depth = 0;
        this.ghoulFactory = ghoulFactory;
        this.humanFactory = humanFactory;
    }

    @Override
    public Demon createProduct() {
        Random random = new Random();

        String name = ConsoleInput.readString("Ponle un nombre a tu esbirro demonio: ");
        int health = random.nextInt(3) + 1;
        String pact = "Pacto a sacar al azar de un fichero de la BBDD";

        Demon demon = new Demon(name, health, pact);

        if (minionOwner == null) {
            this.characterOwner.addMinion(demon);
            System.out.println(name + " añadido como esbirro de " + this.characterOwner.getName() + " con éxito.");
        } else {
            this.minionOwner.addMinion(demon);
            System.out.println(name + " añadido como esbirro del esbirro " + this.minionOwner.getName() + " con éxito.");
        }


        int maxDepth = 2; // Number of levels of "depth" that the demon minions can have
        // If we are already in maxDepth, this new demon won't have more minions.
        if (depth < maxDepth) {
            manageMinionsCreation(demon);
        } else {
            System.out.println("Este demonio ha alcanzado el nivel máximo de profundidad y no tendrá esbirros propios.");
        }

        return demon;
    }

    public void setMinionOwner(Demon minion) {
        this.minionOwner = minion;
    }

    private void manageMinionsCreation(Demon demon) {
        boolean keepCreatingMinions = true;
        int minionCount = 0; // Local counter for the limit of minions

        // Save the previous demon state
        Demon previousOwner = this.minionOwner;

        // Configure the Factories for the new demon
        setMinionOwner(demon);
        ghoulFactory.setMinionOwner(demon);
        humanFactory.setMinionOwner(demon);
        depth += 1; // One extra level of depth

        System.out.println("------------------------------------------------");
        System.out.println("Ahora vamos a crear los esbirros de " + demon.getName() + " (Máx 3):");

        // Condition to stop at three minions
        while (keepCreatingMinions && minionCount < 3) {
            System.out.println("Esbirros actuales: " + minionCount + "/3");
            System.out.println("1. Demonio | 2. Ghoul | 3. Humano | 4. Salir");
            int option = ConsoleInput.readInt(1, 4);

            switch (option) {
                case 1 -> { createProduct(); minionCount++; }
                case 2 -> { ghoulFactory.createProduct(); minionCount++; }
                case 3 -> { humanFactory.createProduct(); minionCount++; }
                default -> keepCreatingMinions = false;
            }
        }

        if (minionCount == 3) {
            System.out.println("Se ha alcanzado el límite de 3 esbirros para " + demon.getName() + ".");
        }

        System.out.println("Terminamos de crear los esbirros de " + demon.getName() + ".");
        System.out.println("------------------------------------------------");

        // Restore the previous state, so the father can continue
        setMinionOwner(previousOwner);
        ghoulFactory.setMinionOwner(previousOwner);
        humanFactory.setMinionOwner(previousOwner);
        depth -= 1; // Lose the level of depth
    }
}
