package Game;


/**
 * Factory class that helps with the creation of werewolf-type characters.
 */
public class WerewolfFactory extends CharacterFactory {

    @Override
    public Werewolf createProduct() {
        String name = ConsoleInput.readString("Nombre del hombre lobo: ");
        System.out.println("Altura de " + name + " en su forma humana (en metros): ");
        double height = ConsoleInput.readDouble(1, 3);
        System.out.println("Peso de " + name + " en su forma humana (en kilogramos): ");
        double weight = ConsoleInput.readDouble(20, 150);

        int health = 5; // Every character starts with 5 health.
        int power = 4; // Werewolves have 4 power.
        Werewolf werewolf = new Werewolf(name, health, power, height, weight);

        // Create the special skill for the werewolf
        SpecialSkillManager skillManager = new SpecialSkillManager();
        skillManager.manageSpecialSkills(werewolf);

        super.createCharacterExtras(werewolf);

        return  werewolf;
    }


}
