package Game;


/**
 * Factory class that helps with the creation of hunter-type characters.
 */
public class HunterFactory extends CharacterFactory {

    @Override
    public Hunter createProduct() {
        String name = ConsoleInput.readString("Nombre del cazador: ");

        int health = 5; // Every character starts with 5 health.
        int power = 3; // Hunters are the strongest characters, with 5 power.
        Hunter hunter = new Hunter(name, health, power);

        // Create the special skill for the hunter
        SpecialSkillManager skillManager = new SpecialSkillManager();
        skillManager.manageSpecialSkills(hunter);

        super.createCharacterExtras(hunter);

        return hunter;
    }


}
