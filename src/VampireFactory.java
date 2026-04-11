import java.util.ArrayList;
import java.util.Scanner;

public class VampireFactory implements AbstractFactory {

    @Override
    public void createProduct() {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Nombre del vampiro: ");
        String name = scanner.nextLine();
        System.out.print("Edad del vampiro: ");
        int age = Integer.parseInt(scanner.nextLine());

        int health = 5;
        int power = 3;
        Vampire vampire = new Vampire(name, health, power, age);

        // Create and activate the equipment for the character
        manageEquipmentCreation(vampire);

        // Creation of the minions for the character
        vampire.addMinions();

        scanner.close();
    }

    private void manageEquipmentCreation(Vampire vampire) {
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
        selectedWeaponIndex = Integer.parseInt(scanner.nextLine());
        selectedWeapon1 = weapons.get(selectedWeaponIndex);
        selectedWeapons.add(selectedWeapon1);

        if (selectedWeapon1.getHands() == 1) {

            System.out.print("Como has elegido un arma de una sola mano, puedes activar otra arma diferente de 1 mano:");
            for (int i = 0; i < weapons.size(); i++) {
                Weapon w = weapons.get(i); // Index access
                System.out.print( i + ". " + w.getName() + ": " + w.getHands() + " manos");
            }
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
        selectedArmourIndex = Integer.parseInt(scanner.nextLine());
        selectedArmour = armours.get(selectedArmourIndex);

        vampire.chooseActiveEquipment(weapons, armours, selectedWeapons, selectedArmour);
    }
}
