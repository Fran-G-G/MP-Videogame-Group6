package Game;


/**
 * Factory class that helps with the creation of vampire-type characters.
 */
public class VampireFactory extends CharacterFactory {

    @Override
    public Vampire createProduct() {
        String name = ConsoleInput.readString("Nombre del vampiro: ");
        System.out.println("Edad de " + name + ": ");
        int age = ConsoleInput.readInt(1, 3000);

        int health = 5; // Every character starts with 5 health.
        int power = 3; // Vampires are the weakest characters, with only 3 power.
        Vampire vampire = new Vampire(name, health, power, age);

        // Create the special skill for the vampire
        SpecialSkillManager skillManager = new SpecialSkillManager();
        skillManager.manageSpecialSkills(vampire);

        super.createCharacterExtras(vampire);

        return vampire;
    }


}
