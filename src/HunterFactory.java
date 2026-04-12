import java.util.Random;
import java.util.Scanner;

/**
 * Factory class that helps with the creation of hunter-type characters.
 */
public class HunterFactory extends CharacterFactory {

    @Override
    public void createProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nombre del cazador: ");
        String name = scanner.nextLine();

        int health = 5; // Every character starts with 5 health.
        int power = 3; // Hunters are the strongest characters, with 5 power.
        Hunter hunter = new Hunter(name, health, power);

        // Create the special skill for the hunter
        createTalent(hunter);

        super.createCharacterExtras(hunter);
    }

    private void createTalent(Hunter hunter) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Nombre del talento del cazador: ");
        String name = scanner.nextLine();
        int attack = random.nextInt(3) + 1;
        int defense = random.nextInt(3) + 1;

        Talent talent = new Talent(name, attack, defense);
        hunter.setSkill(talent);
    }
}
