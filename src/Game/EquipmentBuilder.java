package Game;

import java.util.ArrayList;

/**
 * Builder to help with the process of creating new equipment.
 */
public class EquipmentBuilder implements Builder {

    private final ArrayList<Weapon> weapons;
    private final ArrayList<Armour> armours;

    public EquipmentBuilder(){
        this.weapons = new ArrayList<>();
        this.armours = new ArrayList<>();
    }

    @Override
    public ArrayList<Weapon> buildWeapons() {
        boolean createWeapons = ConsoleInput.readBoolean("¿Quieres crear un arma?");

        while (createWeapons) {
            String name = ConsoleInput.readString("\t Nombre para el arma: ");
            System.out.println("\t Valor de ataque  del arma: ");
            int attackModifier = ConsoleInput.readInt(1, 3);
            System.out.println("\t Valor de defensa del arma: ");
            int defenseModifier = ConsoleInput.readInt(0, 3);
            System.out.println("\t Manos necesarias para utilizar el arma: ");
            int hands = ConsoleInput.readInt(1, 2);
            Weapon newWeapon = new Weapon(name, attackModifier, defenseModifier, hands);

            weapons.add(newWeapon);

            createWeapons = ConsoleInput.readBoolean("¿Quieres crear más armas?");
        }

        return weapons;
    }

    @Override
    public ArrayList<Armour> buildArmours() {
        boolean createArmours = ConsoleInput.readBoolean("¿Quieres crear una armadura?");

        while (createArmours) {
            String name = ConsoleInput.readString("Nombre para la armadura: ");
            System.out.println("Valor de ataque  de la armadura: ");
            int attackModifier = ConsoleInput.readInt(0, 3);
            System.out.println("Valor de defensa de la armadura: ");
            int defenseModifier = ConsoleInput.readInt(1, 3);
            Armour newArmour = new Armour(name, attackModifier, defenseModifier);

            armours.add(newArmour);

            createArmours = ConsoleInput.readBoolean("¿Quieres crear más armaduras?");
        }

        return  armours;
    }

}
