import java.util.ArrayList;
import java.util.Scanner;

/**
 * Builder to help with the process of creating new equipment.
 */
public class EquipmentBuilder implements Builder {

    private ArrayList<Weapon> weapons;
    private ArrayList<Armour> armours;

    public EquipmentBuilder(){
        this.weapons = new ArrayList<>();
        this.armours = new ArrayList<>();
    }

    @Override
    public ArrayList<Weapon> buildWeapons() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("¿Quieres crear armas?");
        System.out.println("Sí: 1");
        System.out.println("No: 0");
        boolean createWeapons = Boolean.parseBoolean(scanner.nextLine());

        while (createWeapons) {
            System.out.println("Nombre para el arma: ");
            String name = scanner.nextLine();
            System.out.println("Valor de ataque  del arma: ");
            int attackModifier = Integer.parseInt(scanner.nextLine());
            System.out.println("Valor de defensa del arma: ");
            int defenseModifier = Integer.parseInt(scanner.nextLine());
            System.out.println("Manos necesarias para utilizar el arma: ");
            int hands = Integer.parseInt(scanner.nextLine());
            Weapon newWeapon = new Weapon(name, attackModifier, defenseModifier, hands);

            weapons.add(newWeapon);

            System.out.println("¿Quieres crear más armas?");
            System.out.println("Sí: 1");
            System.out.println("No: 0");
            createWeapons = Boolean.parseBoolean(scanner.nextLine());
        }

        scanner.close();
        return weapons;
    }

    @Override
    public ArrayList<Armour> buildArmours() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("¿Quieres crear armaduras?");
        System.out.println("Sí: 1");
        System.out.println("No: 0");
        boolean createArmours = Boolean.parseBoolean(scanner.nextLine());

        while (createArmours) {
            System.out.println("Nombre para la armadura: ");
            String name = scanner.nextLine();
            System.out.println("Valor de ataque  de la armadura: ");
            int attackModifier = Integer.parseInt(scanner.nextLine());
            System.out.println("Valor de defensa de la armadura: ");
            int defenseModifier = Integer.parseInt(scanner.nextLine());
            Armour newArmour = new Armour(name, attackModifier, defenseModifier);

            armours.add(newArmour);

            System.out.println("¿Quieres crear más armaduras?");
            System.out.println("Sí: 1");
            System.out.println("No: 0");
            createArmours = Boolean.parseBoolean(scanner.nextLine());
        }

        scanner.close();
        return  armours;
    }

}
