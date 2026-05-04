package Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeaknessManager {

    private final int numWeaknesses = 2;

    public void manageWeaknesses(AbstractCharacter character, boolean creation) {
        if (creation) {
            manageWeaknessesCreation(character);
        } else {
            System.out.println("\n--- Gestión de Debilidades ---");

            showActiveWeaknesses(character.getWeaknesses());

            boolean editing = true;
            while (editing) {
                System.out.println("\n¿Quiere (1) eliminar debilidades, (2) añadir nuevas o (3) cancelar?");
                int option = ConsoleInput.readInt(1, 3);
                switch (option) {
                    case 1 -> removeActiveWeaknesses(character, character.getWeaknesses());
                    case 2 -> {
                        // Load all possible ones from the file
                        ArrayList<Weakness> possibleWeaknesses = getPossibleWeaknesses(character);
                        // Pass the current character list to filter
                        showAndAddWeaknesses(character, possibleWeaknesses);
                    }
                    case 3 -> editing = false;
                    default -> System.out.println("Opción no válida.");
                }
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

    private void showActiveWeaknesses(List<Weakness> weaknesses) {
        System.out.println("Debilidades actuales:");
        for (int i = 0; i < weaknesses.size(); i++) {
            Weakness w = weaknesses.get(i);
            System.out.print("\t" + (i + 1) + ". " + w.getName() + " (Sensibilidad: " + w.getValue() + ")\n");
        }
    }

    private void manageWeaknessesCreation(AbstractCharacter character) {
        ArrayList<Weakness> possibleWeaknesses = getPossibleWeaknesses(character);
        System.out.println("Se elegirán dos debilidades al azar para tu personaje:");

        Collections.shuffle(possibleWeaknesses);
        for (int i = 0; i < numWeaknesses; i++) {
            Weakness selectedWeakness = possibleWeaknesses.get(i);
            System.out.println("\t - " + selectedWeakness.getName() + " (Sensibilidad: " + selectedWeakness.getValue() + ")");
            character.addWeakness(selectedWeakness);
        }
        System.out.println();
    }

    private void removeActiveWeaknesses(AbstractCharacter character, List<Weakness> activeWeaknesses) {
        if (activeWeaknesses.isEmpty()) {
            System.out.println("No hay debilidades para eliminar.");
            return;
        }

        showActiveWeaknesses(activeWeaknesses);
        System.out.println("\n¿Qué debilidad deseas eliminar? (0 para cancelar):");
        int option = ConsoleInput.readInt(0, activeWeaknesses.size());

        if (option > 0) {
            String removedName = activeWeaknesses.get(option - 1).getName();
            character.removeWeakness(option - 1);
            System.out.println("Has eliminado: " + removedName + ".");
        }
    }

    private void showAndAddWeaknesses(AbstractCharacter character, List<Weakness> possibleWeaknesses) {
        List<Weakness> activeWeaknesses = character.getWeaknesses();

        // 1. Validate limit
        if (activeWeaknesses.size() >= numWeaknesses) {
            System.out.println("El personaje ya tiene el máximo de debilidades (" + numWeaknesses + "). Elimina una primero.");
            return;
        }

        // 2. Filter options, showing only the options that the character does NOT already have
        List<Weakness> availableOptions = new ArrayList<>();
        for (Weakness p : possibleWeaknesses) {
            boolean alreadyHasIt = false;
            for (Weakness a : activeWeaknesses) {
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
            System.out.println("No hay más debilidades disponibles en la base de datos.");
            return;
        }

        // 3. Show and select
        System.out.println("Selecciona una nueva debilidad (0 para cancelar):");
        for (int i = 0; i < availableOptions.size(); i++) {
            Weakness w = availableOptions.get(i);
            System.out.print("\t" + (i + 1) + ". " + w.getName() + " (" + w.getValue() + ")\n");
        }

        int selectedIndex = ConsoleInput.readInt(0, availableOptions.size());

        if (selectedIndex > 0) {
            Weakness chosen = availableOptions.get(selectedIndex - 1);
            character.addWeakness(chosen);
            System.out.println("Añadida: " + chosen.getName());
        }
    }
}