package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EquipmentManager {

    public void manageEquipment(AbstractCharacter character) {
        EquipmentBuilder equipmentBuilder = new EquipmentBuilder();

        // 1. Obtain the equipment that the character already has (nothing if it's the first time)
        List<Weapon> weapons = character.getWeapons();
        if (weapons == null) weapons = new ArrayList<>();

        List<Armour> armours = character.getArmours();
        if (armours == null) armours = new ArrayList<>();

        // 2. Build the new equipment and add it to what we already had (nothing if it's the first time)
        weapons.addAll(equipmentBuilder.buildWeapons());
        armours.addAll(equipmentBuilder.buildArmours());

        // 3. We choose which weapon(s) and armor to equip
        List<Weapon> selectedWeapons = new ArrayList<>();
        if (weapons.size() == 1) {
            System.out.println("Como tu personaje únicamente tiene un arma, es la que tendrá activa.\n");

            selectedWeapons.add(weapons.getFirst());
        } else if (!weapons.isEmpty()) {
            System.out.print("De las armas que has creado, elige una como activa:\n");
            showAndSelectWeapon(weapons, selectedWeapons, false);

            // If we choose a weapon, and it's one-handed, we try to choose the second hand.
            if (!selectedWeapons.isEmpty() && selectedWeapons.getFirst().getHands() == 1) {
                System.out.print("Como has elegido un arma de una sola mano, puedes activar una segunda arma de 1 mano (opcional):\n");
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

        // 4. Save the changes to the character
        character.chooseActiveEquipment(weapons, armours, selectedWeapons, selectedArmour);
    }

    private void showAndSelectWeapon(List<Weapon> weapons, List<Weapon> selectedWeapons, boolean secondWeapon) {
        List<Weapon> availableOptions = new ArrayList<>();

        // 1. Filtering valid options
        if (secondWeapon) {
            Weapon firstWeapon = selectedWeapons.getFirst();
            for (Weapon w : weapons) {
                // Only one-handed weapons, other than the one we already chose
                if (w.getHands() == 1 && !Objects.equals(w.getName(), firstWeapon.getName())) {
                    availableOptions.add(w);
                }
            }
        } else {
            availableOptions.addAll(weapons);
        }

        // 2. Security check
        if (availableOptions.isEmpty()) {
            if (secondWeapon) {
                System.out.println("\tNo tienes más armas de 1 mano compatibles.\n");
            } else {
                System.out.println("\tNo tienes armas disponibles.\n");
            }
            return;
        }

        // 3. Show options
        System.out.println("\t0. No equipar segunda arma");

        for (int i = 0; i < availableOptions.size(); i++) {
            Weapon w = availableOptions.get(i);
            System.out.print("\t" + (i + 1) + ". " + w.getName() + " (" + w.getHands() + " mano/s)\n");
        }

        // 4. Selection, with "Cancel" option if it's the second weapon
        int selectedIndex;
        if (secondWeapon) {
            selectedIndex = ConsoleInput.readInt(0, availableOptions.size());
        } else {
            selectedIndex = ConsoleInput.readInt(1, availableOptions.size());
        }

        // 5. Add to list only if you did not choose "0"
        if (selectedIndex > 0) {
            selectedWeapons.add(availableOptions.get(selectedIndex - 1));
        }
    }
}
