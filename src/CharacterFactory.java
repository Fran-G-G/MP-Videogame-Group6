import java.util.ArrayList;
import java.util.Scanner;

/**
 * Base Factory for creating the common part of all types of characters.
 */
public abstract class CharacterFactory implements AbstractFactory {

    @Override
    public abstract void createProduct();

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

    }

    private void manageStrengthsCreation(AbstractCharacter character) {

    }

    private void manageEquipmentCreation(AbstractCharacter character) {
        Scanner scanner = new Scanner(System.in);

        EquipmentBuilder equipmentBuilder = new EquipmentBuilder();
        ArrayList<Weapon> weapons = equipmentBuilder.buildWeapons();
        ArrayList<Armour> armours = equipmentBuilder.buildArmours();

        int selectedWeaponIndex;
        Weapon selectedWeapon1;
        Weapon selectedWeapon2;
        ArrayList<Weapon> selectedWeapons = new ArrayList<>();
        System.out.print("De las armas que has creado, elige una como activa:");
        for (int i = 0; i < weapons.size(); i++) {
            Weapon w = weapons.get(i); // Index access
            System.out.print( i + ". " + w.getName() + ": " + w.getHands() + " manos");
        }
        System.out.print("Opción: ");
        selectedWeaponIndex = Integer.parseInt(scanner.nextLine());
        selectedWeapon1 = weapons.get(selectedWeaponIndex);
        selectedWeapons.add(selectedWeapon1);

        if (selectedWeapon1.getHands() == 1) {

            System.out.print("Como has elegido un arma de una sola mano, puedes activar otra arma diferente de 1 mano:");
            for (int i = 0; i < weapons.size(); i++) {
                Weapon w = weapons.get(i); // Index access
                System.out.print( i + ". " + w.getName() + ": " + w.getHands() + " manos");
            }
            System.out.print("Opción: ");
            selectedWeaponIndex = Integer.parseInt(scanner.nextLine());
            selectedWeapon2 = weapons.get(selectedWeaponIndex);
            selectedWeapons.add(selectedWeapon2);
        }

        int selectedArmourIndex;
        Armour selectedArmour;
        System.out.print("De las armaduras que has creado, elige una como activa:");
        for (int i = 0; i < armours.size(); i++) {
            Armour a = armours.get(i); // Index access
            System.out.print( i + ". " + a.getName() );
        }
        System.out.print("Opción: ");
        selectedArmourIndex = Integer.parseInt(scanner.nextLine());
        selectedArmour = armours.get(selectedArmourIndex);

        character.chooseActiveEquipment(weapons, armours, selectedWeapons, selectedArmour);
    }

    private void manageMinionsCreation(AbstractCharacter character) {
        DemonFactory demonFactory = new DemonFactory();
        GhoulFactory ghoulFactory = new GhoulFactory();
        HumanFactory humanFactory = new HumanFactory();
        boolean keepCreatingMinions = true;

        while (keepCreatingMinions) {
            System.out.println("Elige el tipo de esbirro que quieras crear, o pulsa 4 para terminar:");
            System.out.println("1. Demonio");
            System.out.println("2. Ghoul");
            System.out.println("3. Humano");
            System.out.println("4. Salir");
            int option = readInt(1, 4);

            switch (option) {
                case 1 -> demonFactory.createProduct();
                case 2 -> ghoulFactory.createProduct();
                case 3 -> humanFactory.createProduct();
                default -> keepCreatingMinions = false;
            }
        }
    }

    /**
     * Reads integer safely.
     */
    private int readInt(int min, int max) {
        Scanner scanner = new Scanner(System.in);

        int value;

        while (true) {
            try {
                System.out.print("Opción: ");
                value = Integer.parseInt(scanner.nextLine());

                if (value >= min && value <= max) {
                    scanner.close();
                    return value;
                }

            } catch (Exception ignored) {}

            System.out.println("Valor inválido. Intenta de nuevo.");
        }
    }
}
