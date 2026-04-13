import java.util.ArrayList;
import java.util.Collections;

/**
 * Base Factory for creating the common part of all types of characters.
 */
public abstract class CharacterFactory implements AbstractFactory<AbstractCharacter> {

    private final int numWeakStrg = 2;

    @Override
    public abstract AbstractCharacter createProduct();

    protected void createCharacterExtras(AbstractCharacter character) {

        // Create and add the weaknesses to the character
        manageWeaknessesCreation(character);

        // Create and add the strengths to the character
        manageStrengthsCreation(character);

        // Create and activate the equipment for the character
        manageEquipmentCreation(character);

        // Creation of the minions for the character
        manageMinionsCreation(character);
    }

    private void manageWeaknessesCreation(AbstractCharacter character) {

        ArrayList<Weakness> possibleWeaknesses = ConfigReader.loadAttributes(
                character.getClass().getSimpleName(), // Returns "Vampire" or "Werewolf" or "Hunter" or ...
                "weaknesses",
                Weakness::new
        );

        System.out.println("Se elegirán dos debilidades al azar para tu personaje:");

        Collections.shuffle(possibleWeaknesses);
        for (int i = 0; i < numWeakStrg; i++) {
            Weakness selectedWeakness = possibleWeaknesses.get(i);
            System.out.println("Nombre de la debilidad: " + selectedWeakness.getName() + ". Sensibilidad: " + selectedWeakness.getValue());

            character.addWeakness(selectedWeakness);
        }
    }

    private void manageStrengthsCreation(AbstractCharacter character) {

        ArrayList<Strength> possibleStrengths = ConfigReader.loadAttributes(
                character.getClass().getSimpleName(), // Returns "Vampire" or "Werewolf" or "Hunter" or ...
                "strengths",
                Strength::new
        );

        System.out.println("Se elegirán dos fortalezas al azar para tu personaje:");

        Collections.shuffle(possibleStrengths);
        for (int i = 0; i < numWeakStrg; i++) {
            Strength selectedStrength = possibleStrengths.get(i);
            System.out.println("Nombre de la fortaleza: " + selectedStrength.getName() + ". Temple: " + selectedStrength.getValue());

            character.addStrength(selectedStrength);
        }
    }

    private void manageEquipmentCreation(AbstractCharacter character) {
        EquipmentBuilder equipmentBuilder = new EquipmentBuilder();
        ArrayList<Weapon> weapons = equipmentBuilder.buildWeapons();
        ArrayList<Armour> armours = equipmentBuilder.buildArmours();

        ArrayList<Weapon> selectedWeapons = new ArrayList<>();

        System.out.print("De las armas que has creado, elige una como activa:");
        showAndSelectWeapon(weapons, selectedWeapons);

        if (selectedWeapons.getFirst().getHands() == 1) {
            System.out.print("Como has elegido un arma de una sola mano, puedes activar otra arma diferente de 1 mano:");
            showAndSelectWeapon(weapons, selectedWeapons);
        }

        int selectedArmourIndex;
        Armour selectedArmour;
        System.out.print("De las armaduras que has creado, elige una como activa:");
        for (int i = 0; i < armours.size(); i++) {
            Armour a = armours.get(i); // Index access
            System.out.print( i+1 + ". " + a.getName() );
        }
        System.out.print("Opción: ");
        selectedArmourIndex = ConsoleInput.readInt(0, armours.size()-1);
        selectedArmour = armours.get(selectedArmourIndex);

        character.chooseActiveEquipment(weapons, armours, selectedWeapons, selectedArmour);
    }

    private void showAndSelectWeapon(ArrayList<Weapon> weapons, ArrayList<Weapon> selectedWeapons) {
        for (int i = 0; i < weapons.size(); i++) {
            Weapon w = weapons.get(i); // Index access
            System.out.print( i+1 + ". " + w.getName() + ": " + w.getHands() + " manos");
        }

        System.out.print("Opción: ");
        int selectedWeaponIndex = ConsoleInput.readInt(0, weapons.size()-1);

        Weapon selectedWeapon = weapons.get(selectedWeaponIndex);
        selectedWeapons.add(selectedWeapon);
    }

    private void manageMinionsCreation(AbstractCharacter character) {
        GhoulFactory ghoulFactory = new GhoulFactory(character, null);
        HumanFactory humanFactory = new HumanFactory(character, null);
        DemonFactory demonFactory = new DemonFactory(character, null, ghoulFactory, humanFactory);
        boolean keepCreatingMinions = true;
        int minionCount = 0;

        while (keepCreatingMinions && minionCount < 5) {
            System.out.println("Elige el tipo de esbirro que quieras crear, o pulsa 4 para terminar:");
            System.out.println("1. Demonio | 2. Ghoul | 3. Humano | 4. Salir");
            System.out.println("\nEsbirros actuales: " + minionCount + "/5");
            int option = ConsoleInput.readInt(1, 4);

            switch (option) {
                case 1 -> { demonFactory.createProduct(); minionCount++; }
                case 2 -> { ghoulFactory.createProduct(); minionCount++; }
                case 3 -> { if (confirmNotVampire(character, humanFactory)) {minionCount++;} }
                default -> keepCreatingMinions = false;
            }
        }

        if (minionCount == 5) {
            System.out.println("Se ha alcanzado el límite de 5 esbirros para " + character.getName() + ".");
        }
    }

    private boolean confirmNotVampire(AbstractCharacter character, HumanFactory humanFactory) {
        if (character.getClass().getSimpleName().equals("Vampire")) {
            System.out.println("Los personajes de tipo vampiro no pueden tener esbirros humanos, ¡se los comerían!.");
            return false;
        } else {
            humanFactory.createProduct();
            return true;
        }
    }
}
