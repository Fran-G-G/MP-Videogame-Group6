package Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StrengthManager {

    private final int numStrengths = 2;

    public void manageStrengths(AbstractCharacter character, boolean creation) {
        if (creation) {
            manageStrengthsCreation(character);
        } else {
            ArrayList<Strength> possibleStrengths = getPossibleStrengths(character);
        }
    }

    private ArrayList<Strength> getPossibleStrengths(AbstractCharacter character) {
        return ConfigReader.loadAttributes(
                character.getClass().getSimpleName(), // Returns "Vampire" or "Werewolf" or "Hunter" or ...
                "strengths",
                Strength::new
        );
    }

    private void showActiveStrengths(AbstractCharacter character) {

    }

    private void manageStrengthsCreation(AbstractCharacter character) {

        ArrayList<Strength> possibleStrengths = getPossibleStrengths(character);

        System.out.println("Se elegirán dos fortalezas al azar para tu personaje:");

        Collections.shuffle(possibleStrengths);
        for (int i = 0; i < numStrengths; i++) {
            Strength selectedStrength = possibleStrengths.get(i);
            System.out.println("\t Nombre de la fortaleza: " + selectedStrength.getName() + ". Temple: " + selectedStrength.getValue());

            character.addStrength(selectedStrength);
        }

        System.out.println();
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
        for (int i = 0; i < availableOptions.size(); i++) {
            Weakness w = availableOptions.get(i);
            System.out.print("\t" + (i + 1) + ". " + w.getName() + " (" + w.getValue() + ")\n");
        }

        // 4. Selection, with "Cancel" option if it's the second weapon
        int selectedIndex;
        if (firstWeakness) {
            System.out.println("\t0. No equipar segunda arma");
            selectedIndex = ConsoleInput.readInt(0, availableOptions.size());
        } else {
            selectedIndex = ConsoleInput.readInt(1, availableOptions.size());
        }

        // 5. Add to list only if you did not choose "0"
        if (selectedIndex > 0) {
            selectedWeaknesses.add(availableOptions.get(selectedIndex - 1));
        }
    }
}
