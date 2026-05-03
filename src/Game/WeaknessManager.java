package Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class WeaknessManager {

    private final int numWeaknesses = 2;

    public void manageWeaknesses(AbstractCharacter character, boolean creation) {
        if (creation) {
            manageWeaknessesCreation(character);
        } else {
            System.out.println("¿Quiere (1) eliminar debilidades del personaje o (2) añadir nuevas debilidades?");
            int option = ConsoleInput.readInt(1,2);
            switch (option) {
                //case 1 -> removeActiveWeaknesses(character, character.getWeaknesses());
                case 2 -> {
                    ArrayList<Weakness> possibleWeaknesses = getPossibleWeaknesses(character);
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private ArrayList<Weakness> getPossibleWeaknesses(AbstractCharacter character) {
        return ConfigReader.loadAttributes(
                character.getClass().getSimpleName(), // Returns "Vampire" or "Werewolf" or "Hunter" or ...
                "weaknesses",
                Weakness::new
        );
    }

    private void showActiveWeaknesses(AbstractCharacter character) {

    }

    private void manageWeaknessesCreation(AbstractCharacter character) {

        ArrayList<Weakness> possibleWeaknesses = getPossibleWeaknesses(character);

        System.out.println("Se elegirán dos debilidades al azar para tu personaje:");

        Collections.shuffle(possibleWeaknesses);
        for (int i = 0; i < numWeaknesses; i++) {
            Weakness selectedWeakness = possibleWeaknesses.get(i);
            System.out.println("\t Nombre de la debilidad: " + selectedWeakness.getName() + ". Sensibilidad: " + selectedWeakness.getValue());

            character.addWeakness(selectedWeakness);
        }

        System.out.println();
    }

    private void removeActiveWeaknesses(AbstractCharacter character, List<Weakness> activeWeaknesses) {
        if (activeWeaknesses.isEmpty()) {
            System.out.println("No hay debilidades para eliminar.");
            return;
        }

        //showActiveWeaknesses(activeWeaknesses);

        System.out.println("¿Qué debilidad deseas eliminar? (Escribe su número o 0 para cancelar):");
        int option = ConsoleInput.readInt(0, activeWeaknesses.size());

        if (option > 0) {
            System.out.println("Has eliminado a " + activeWeaknesses.get(option - 1).getName() + ".");
            character.removeMinion(option - 1);
        }
    }

    private void showAndSelectWeaknesses(List<Weakness> selectedWeaknesses, List<Weakness> possibleWeaknesses, boolean firstWeakness) {
        List<Weakness> availableOptions = new ArrayList<>();

        // 1. Filtering valid options
        if (!firstWeakness) {
            Weakness previousWeakness = selectedWeaknesses.getFirst();
            for (Weakness w : possibleWeaknesses) {
                // Only one-handed weapons, other than the one we already chose
                if (!Objects.equals(w.getName(), previousWeakness.getName())) {
                    availableOptions.add(w);
                }
            }
        } else {
            availableOptions.addAll(possibleWeaknesses);
        }

        // 2. Security check
        if (availableOptions.isEmpty()) {
            if (!firstWeakness) {
                System.out.println("\tNo hay más debilidades disponibles.\n");
            } else {
                System.out.println("\tNo hay debilidades disponibles.\n");
            }
            return;
        }

        // 3. Show options
        System.out.println("\t0. No seleccionar ninguna debilidad");

        for (int i = 0; i < availableOptions.size(); i++) {
            Weakness w = availableOptions.get(i);
            System.out.print("\t" + (i + 1) + ". " + w.getName() + " (" + w.getValue() + ")\n");
        }

        // 4. Selection, with "Cancel" option
        int selectedIndex = ConsoleInput.readInt(0, availableOptions.size());

        // 5. Add to list only if you did not choose "0"
        if (selectedIndex > 0) {
            selectedWeaknesses.add(availableOptions.get(selectedIndex - 1));
        }
    }
}
