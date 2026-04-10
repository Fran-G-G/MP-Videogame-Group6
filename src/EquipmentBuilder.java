import java.util.ArrayList;
import java.util.Scanner;

public class EquipmentBuilder implements Builder {

    private Scanner scanner;
    private ArrayList<Weapon> weapons;

    public EquipmentBuilder(){
        this.weapons = new ArrayList<>();
    }

    public ArrayList<Weapon> build(){

        System.out.println("¿Quieres crear armas?");
        System.out.println("Sí: 1");
        System.out.println("No: 0");
        boolean createWeapons = Boolean.parseBoolean(scanner.nextLine());

        while (createWeapons) {
            System.out.println("Nombre para el arma:");
            String name = scanner.nextLine();
            System.out.println("Valor de ataque  del arma:");
            int attackModifier = Integer.parseInt(scanner.nextLine());
            System.out.println("Valor de defensa del arma:");
            int defenseModifier = Integer.parseInt(scanner.nextLine());
            System.out.println("Manos necesarias para utilizar el arma:");
            int hands = Integer.parseInt(scanner.nextLine());
            Weapon newWeapon = new Weapon(name, attackModifier, defenseModifier, hands);

            weapons.add(newWeapon);

            System.out.println("¿Quieres crear más armas?");
            System.out.println("Sí: 1");
            System.out.println("No: 0");
            createWeapons = Boolean.parseBoolean(scanner.nextLine());
        }

        return weapons;
    }

}
