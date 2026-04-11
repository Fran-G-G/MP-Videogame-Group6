import java.util.Scanner;

public class VampireFactory extends CharacterFactory {

    @Override
    public void createProduct() {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Nombre del vampiro: ");
        String name = scanner.nextLine();
        System.out.print("Edad del vampiro: ");
        int age = Integer.parseInt(scanner.nextLine());

        int health = 5; // Every character starts with 5 health.
        int power = 3; // Vampires are the weakest characters, with only 3 power.
        Vampire vampire = new Vampire(name, health, power, age);

        // Create and activate the equipment for the character
        super.manageEquipmentCreation(vampire);

        // Creation of the minions for the character
        super.manageMinionsCreation(vampire);

        scanner.close();
    }
}
