package Game;

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
        MinionManager minionManager = new MinionManager();
        minionManager.addMinionsToCharacter(character);
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
}
