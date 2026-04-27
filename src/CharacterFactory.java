import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Base Factory for creating the common part of all types of characters.
 */
public abstract class CharacterFactory implements AbstractFactory<AbstractCharacter> {

    private final int numWeakStrg = 2;

    @Override
    public abstract AbstractCharacter createProduct();

    protected void createCharacterExtras(AbstractCharacter character) {

        System.out.println("-----------------------------------------------------------------------------\n");
        // Create and add the weaknesses to the character
        manageWeaknessesCreation(character);

        // Create and add the strengths to the character
        manageStrengthsCreation(character);
        System.out.println("-----------------------------------------------------------------------------\n");

        // Create and activate the equipment for the character
        EquipmentManager eqManager = new EquipmentManager();
        eqManager.manageEquipment(character);

        System.out.println("-----------------------------------------------------------------------------\n");

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
            System.out.println("\t Nombre de la debilidad: " + selectedWeakness.getName() + ". Sensibilidad: " + selectedWeakness.getValue());

            character.addWeakness(selectedWeakness);
        }

        System.out.println();
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
            System.out.println("\t Nombre de la fortaleza: " + selectedStrength.getName() + ". Temple: " + selectedStrength.getValue());

            character.addStrength(selectedStrength);
        }

        System.out.println();
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
