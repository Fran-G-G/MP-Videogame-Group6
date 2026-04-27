package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EquipmentManager {

    public void manageEquipment(AbstractCharacter character) {
        EquipmentBuilder equipmentBuilder = new EquipmentBuilder();

        // 1. Obtenemos el equipo que ya tiene el personaje (nada si es la primera vez)
        List<Weapon> weapons = character.getWeapons();
        if (weapons == null) weapons = new ArrayList<>();

        List<Armour> armours = character.getArmours();
        if (armours == null) armours = new ArrayList<>();

        // 2. Construimos el nuevo equipo y lo añadimos a lo que ya tenía (nada si es la primera vez)
        weapons.addAll(equipmentBuilder.buildWeapons());
        armours.addAll(equipmentBuilder.buildArmours());

        // 3. Elegimos que arma/s y armadura equipar
        List<Weapon> selectedWeapons = new ArrayList<>();
        if (weapons.size() == 1) {
            System.out.println("Como tu personaje únicamente tiene un arma, es la que tendrá activa.\n");

            selectedWeapons.add(weapons.getFirst());
        } else if (!weapons.isEmpty()) {
            System.out.print("De las armas que has creado, elige una como activa:\n");
            showAndSelectWeapon(weapons, selectedWeapons, false);

            if (selectedWeapons.getFirst().getHands() == 1) {
                System.out.print("Como has elegido un arma de una sola mano, puedes activar otra arma diferente de 1 mano:\n");
                showAndSelectWeapon(weapons, selectedWeapons, true);
            }
        } else {
            System.out.println("Tu personaje no tiene armas de entre las que poder elegir.\n");
        }

        Armour selectedArmour = null;
        if (armours.size() == 1) {
            System.out.println("Como tu personaje únicamente tiene una armadura, es la que tendrá activa.\n");

            selectedArmour = armours.getFirst();
        } else if (!armours.isEmpty()) {
            int selectedArmourIndex;

            System.out.print("De las armaduras que has creado, elige una como activa:\n");
            for (int i = 0; i < armours.size(); i++) {
                Armour a = armours.get(i); // Index access
                System.out.print("\t" + (i + 1) + ". " + a.getName() + "\n");
            }

            selectedArmourIndex = ConsoleInput.readInt(1, armours.size());
            selectedArmour = armours.get(selectedArmourIndex - 1);
        } else {
            System.out.println("Tu personaje no tiene armaduras de entre las que poder elegir.\n");
        }

        // 4. Guardamos los cambios en el personaje
        character.chooseActiveEquipment(weapons, armours, selectedWeapons, selectedArmour);
    }

    private void showAndSelectWeapon(List<Weapon> weapons, List<Weapon> selectedWeapons, boolean secondWeapon) {
        List<Weapon> auxWeaponsArray = new ArrayList<>();

        for (int i = 0; i < weapons.size(); i++) {
            Weapon w = weapons.get(i); // Index access

            if (secondWeapon && (w.getHands() != 2) && (!Objects.equals(w.getName(), selectedWeapons.getFirst().getName()))) {
                System.out.print("\t" + (auxWeaponsArray.size() + 1) + ". " + w.getName() + "\n");
                auxWeaponsArray.add(w);
            } else if (!secondWeapon) {
                System.out.print("\t" + (i + 1) + ". " + w.getName() + ": " + w.getHands() + " mano/s\n");

            }
        }

        int selectedWeaponIndex;
        Weapon selectedWeapon;
        if (!secondWeapon) {
            selectedWeaponIndex = ConsoleInput.readInt(1, weapons.size());

            selectedWeapon = weapons.get(selectedWeaponIndex-1);
        } else {
            selectedWeaponIndex = ConsoleInput.readInt(1, auxWeaponsArray.size());

            selectedWeapon = auxWeaponsArray.get(selectedWeaponIndex-1);
        }

        selectedWeapons.add(selectedWeapon);
    }
}
