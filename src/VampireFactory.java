import java.util.ArrayList;
import java.util.Random;

/**
 * Factory class that helps with the creation of vampire-type characters.
 */
public class VampireFactory extends CharacterFactory {

    @Override
    public Vampire createProduct() {
        String name = ConsoleInput.readString("Nombre del vampiro: ");
        System.out.print("Edad de " + name + ": ");
        int age = ConsoleInput.readInt(1, 3000);

        int health = 5; // Every character starts with 5 health.
        int power = 3; // Vampires are the weakest characters, with only 3 power.
        Vampire vampire = new Vampire(name, health, power, age);

        // Create the special skill for the vampire
        createDiscipline(vampire);

        super.createCharacterExtras(vampire);

        return vampire;
    }

    private void createDiscipline(Vampire vampire) {
        Random random = new Random();

        String name = ConsoleInput.readString("Nombre de la disciplina del vampiro: ");
        int attack = random.nextInt(3) + 1;
        int defense = random.nextInt(3) + 1;
        int bloodCost = random.nextInt(3) + 1;

        Discipline discipline = new Discipline(name, attack, defense, bloodCost);
        vampire.setSkill(discipline);
    }
}
