package Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StrengthManager {

    private final int numStrengths = 2;

    public void manageStrengths(AbstractCharacter character, boolean creation) {
        if (creation) {
            manageStrengthsCreation(character);
        } else {
            System.out.println("\n--- Gestión de Fortalezas ---");

            showActiveStrengths(character.getStrengths());

            boolean editing = true;
            while (editing) {
                System.out.println("¿Quiere (1) eliminar fortalezas, (2) añadir nuevas o (3) cancelar?");
                int option = ConsoleInput.readInt(1, 3);
                switch (option) {
                    case 1 -> removeActiveStrengths(character, character.getStrengths());
                    case 2 -> {
                        // Load all possible ones from the file
                        ArrayList<Strength> possibleStrengths = getPossibleStrengths(character);
                        // Pass the current character list to filter
                        showAndAddStrengths(character, possibleStrengths);
                    }
                    case 3 -> editing = false;
                    default -> System.out.println("Opción no válida.");
                }
            }
        }
    }

    private ArrayList<Strength> getPossibleStrengths(AbstractCharacter character) {
        return ConfigReader.loadAttributes(
                character.getClass().getSimpleName(), // Returns "Vampire" or "Werewolf" or "Hunter" or ...
                "strengths",
                Strength::new
        );
    }

    private void showActiveStrengths(List<Strength> strengths) {
        System.out.println("Fortalezas actuales:");
        for (int i = 0; i < strengths.size(); i++) {
            Strength s = strengths.get(i);
            System.out.print("\t" + (i + 1) + ". " + s.getName() + " (Temple: " + s.getValue() + ")\n");
        }
    }

    private void manageStrengthsCreation(AbstractCharacter character) {
        ArrayList<Strength> possibleStrengths = getPossibleStrengths(character);
        System.out.println("Se elegirán dos fortalezas al azar para tu personaje:");

        Collections.shuffle(possibleStrengths);
        for (int i = 0; i < numStrengths; i++) {
            Strength selectedStrength = possibleStrengths.get(i);
            System.out.println("\t - " + selectedStrength.getName() + " (Temple: " + selectedStrength.getValue() + ")");
            character.addStrength(selectedStrength);
        }
        System.out.println();
    }

    private void removeActiveStrengths(AbstractCharacter character, List<Strength> activeStrengths) {
        if (activeStrengths.isEmpty()) {
            System.out.println("No hay fortalezas para eliminar.");
            return;
        }

        showActiveStrengths(activeStrengths);
        System.out.println("\n¿Qué fortaleza deseas eliminar? (0 para cancelar):");
        int option = ConsoleInput.readInt(0, activeStrengths.size());

        if (option > 0) {
            String removedName = activeStrengths.get(option - 1).getName();
            character.removeStrength(option - 1);
            System.out.println("Has eliminado: " + removedName + ".");
        }
    }

    private void showAndAddStrengths(AbstractCharacter character, List<Strength> possibleStrengths) {
        List<Strength> activeStrengths = character.getStrengths();

        // 1. Validate limit
        if (activeStrengths.size() >= numStrengths) {
            System.out.println("El personaje ya tiene el máximo de fortalezas (" + numStrengths + "). Elimina una primero.");
            return;
        }

        // 2. Filter options, showing only the options that the character does NOT already have
        List<Strength> availableOptions = new ArrayList<>();
        for (Strength p : possibleStrengths) {
            boolean alreadyHasIt = false;
            for (Strength a : activeStrengths) {
                if (p.getName().equalsIgnoreCase(a.getName())) {
                    alreadyHasIt = true;
                    break;
                }
            }
            if (!alreadyHasIt) {
                availableOptions.add(p);
            }
        }

        if (availableOptions.isEmpty()) {
            System.out.println("No hay más fortalezas disponibles en la base de datos.");
            return;
        }

        // 3. Show and select
        System.out.println("Selecciona una nueva fortaleza (0 para cancelar):");
        for (int i = 0; i < availableOptions.size(); i++) {
            Strength s = availableOptions.get(i);
            System.out.print("\t" + (i + 1) + ". " + s.getName() + " (" + s.getValue() + ")\n");
        }

        int selectedIndex = ConsoleInput.readInt(0, availableOptions.size());

        if (selectedIndex > 0) {
            Strength chosen = availableOptions.get(selectedIndex - 1);
            character.addStrength(chosen);
            System.out.println("Añadida: " + chosen.getName());
        }
    }
}
