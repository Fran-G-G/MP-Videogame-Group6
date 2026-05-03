package Game;


/**
 * Base Factory for creating the common part of all types of characters.
 */
public abstract class CharacterFactory implements AbstractFactory<AbstractCharacter> {

    @Override
    public abstract AbstractCharacter createProduct();

    protected void createCharacterExtras(AbstractCharacter character) {

        System.out.println("-----------------------------------------------------------------------------\n");

        // Create and add the weaknesses to the character
        WeaknessManager weaknessManager = new WeaknessManager();
        weaknessManager.manageWeaknesses(character, true);
        // Create and add the strengths to the character
        StrengthManager strengthManager = new StrengthManager();
        strengthManager.manageStrengths(character, true);

        System.out.println("-----------------------------------------------------------------------------\n");

        // Create and activate the equipment for the character
        EquipmentManager eqManager = new EquipmentManager();
        eqManager.manageEquipment(character);

        System.out.println("-----------------------------------------------------------------------------\n");

        // Creation of the minions for the character
        MinionManager minionManager = new MinionManager();
        minionManager.addMinionsToCharacter(character);
    }
}
