import java.util.Random;
import java.util.Scanner;

/**
 * Factory class that helps with the creation of werewolf-type characters.
 */
public class WerewolfFactory extends CharacterFactory {

    @Override
    public void createProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nombre del hombre lobo: ");
        String name = scanner.nextLine();
        System.out.print("Altura del hombre lobo en su forma humana (en metros): ");
        int height = Integer.parseInt(scanner.nextLine());
        System.out.print("Peso del hombre lobo en su forma humana (en kilogramos): ");
        int weight = Integer.parseInt(scanner.nextLine());

        int health = 5; // Every character starts with 5 health.
        int power = 4; // Werewolves have 4 power.
        Werewolf werewolf = new Werewolf(name, health, power, height, weight);

        // Create the special skill for the character
        createGift(werewolf);

        super.createCharacterExtras(werewolf);

        scanner.close();
    }

    private void createGift(Werewolf werewolf) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Nombre del don del hombre lobo: ");
        String name = scanner.nextLine();
        int attack = random.nextInt(3) + 1;
        int defense = random.nextInt(3) + 1;
        int rageCost = random.nextInt(3) + 1;

        Gift gift = new Gift(name, attack, defense, rageCost);
        werewolf.setSkill(gift);

        scanner.close();
    }
}
