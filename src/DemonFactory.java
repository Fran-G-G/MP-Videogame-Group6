import java.util.Random;
import java.util.Scanner;

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
    public void createProduct() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Ponle un nombre a tu esbirro demonio: ");
        String name = scanner.nextLine();

        int health = random.nextInt(3) + 1;
        String pact = ;

        Demon demon = new Demon(name, health, pact);

        if (minionOwner == null) {
            this.characterOwner.addMinion(demon);
            System.out.println(name + " añadido como esbirro de " + this.characterOwner.getName() + " con éxito.");
        } else {
            this.minionOwner.addMinion(demon);
            System.out.println(name + " añadido como esbirro del esbirro " + this.minionOwner.getName() + " con éxito.");
        }

        manageMinionsCreation(demon);
    }

    public void setMinionOwner(Demon minion) {
        this.minionOwner = minion;
    }

    private void manageMinionsCreation(Demon demon) {
        boolean keepCreatingMinions = true;

        setMinionOwner(demon);
        ghoulFactory.setMinionOwner(demon);
        humanFactory.setMinionOwner(demon);

        depth += 1;

        System.out.println("------------------------------------------------");
        System.out.println("Ahora vamos a crear los esbirros del demonio:");

        while (keepCreatingMinions) {
            System.out.println("Elige el tipo de esbirro que quieras crear, o pulsa 4 para terminar:");
            System.out.println("1. Demonio");
            System.out.println("2. Ghoul");
            System.out.println("3. Humano");
            System.out.println("4. Salir");
            int option = readInt(1, 4);

            switch (option) {
                case 1 -> this.createProduct();
                case 2 -> ghoulFactory.createProduct();
                case 3 -> humanFactory.createProduct();
                default -> keepCreatingMinions = false;
            }
        }

        depth -= 1;

        System.out.println("Terminamos de crear los esbirros del demonio.");
        System.out.println("------------------------------------------------");
    }

    /**
     * Reads integer safely.
     */
    private int readInt(int min, int max) {
        Scanner scanner = new Scanner(System.in);

        int value;

        while (true) {
            try {
                System.out.print("Opción: ");
                value = Integer.parseInt(scanner.nextLine());

                if (value >= min && value <= max) {
                    return value;
                }

            } catch (Exception ignored) {}

            System.out.println("Valor inválido. Intenta de nuevo.");
        }
    }
}
