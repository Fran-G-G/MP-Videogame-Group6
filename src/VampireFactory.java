import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * Factory class that helps with the creation of vampire-type characters.
 */
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

        // Create the special skill for the character
        createDiscipline(vampire);

        super.createCharacterExtras(vampire);

        scanner.close();
    }

    private void createDiscipline(Vampire vampire) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Nombre de la disciplina del vampiro: ");
        String name = scanner.nextLine();
        int attack = random.nextInt(3) + 1;
        int defense = random.nextInt(3) + 1;
        int bloodCost = random.nextInt(3) + 1;

        Discipline discipline = new Discipline(name, attack, defense, bloodCost);
        vampire.setSkill(discipline);

        scanner.close();
    }
}
