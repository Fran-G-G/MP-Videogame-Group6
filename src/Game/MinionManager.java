package Game;

import java.util.ArrayList;
import java.util.List;

public class MinionManager {
    // Main method for editing minions
    public void editMinions(AbstractCharacter character) {
        boolean keepEditing = true;

        while (keepEditing) {
            List<AbstractMinion> minions = character.getAbstractMinions();
            if (minions == null) {
                minions = new ArrayList<>(); // In case the player didn't add minions wile creating the character
            }

            System.out.println("================================================================================");
            System.out.println("--- Editando Esbirros de " + character.getName() + " (" + minions.size() + "/5) ---");
            showMinions(minions);

            System.out.println("\n¿Qué deseas hacer?");
            System.out.println("1. Añadir esbirro | 2. Eliminar esbirro | 3. Volver");
            int option = ConsoleInput.readInt(1, 3);

            switch (option) {
                case 1 -> addMinionsToCharacter(character);
                case 2 -> removeMinion(character, minions);
                case 3 -> keepEditing = false;
            }
        }
    }

    // Main method for creating minions
    public void addMinionsToCharacter(AbstractCharacter character) {
        GhoulFactory ghoulFactory = new GhoulFactory(character, null);
        HumanFactory humanFactory = new HumanFactory(character, null);
        DemonFactory demonFactory = new DemonFactory(character, null, ghoulFactory, humanFactory);

        boolean keepCreatingMinions = true;

        // Get the number of current minions: Variable = (Condition) ? Value_if_true : Value_if_false;
        int minionCount = character.getAbstractMinions() != null ? character.getAbstractMinions().size() : 0;

        if (minionCount >= 5) {
            System.out.println("Ya has alcanzado el límite de 5 esbirros para " + character.getName() + ".");
            return;
        }

        while (keepCreatingMinions && minionCount < 5) {
            System.out.println("Elige el tipo de esbirro que quieras añadir, o pulsa 4 para terminar:");
            System.out.println("1. Demonio | 2. Ghoul | 3. Humano | 4. Salir");
            System.out.println("Esbirros actuales: " + minionCount + "/5");
            int option = ConsoleInput.readInt(1, 4);

            switch (option) {
                case 1 -> { demonFactory.createProduct(); minionCount++; }
                case 2 -> { ghoulFactory.createProduct(); minionCount++; }
                case 3 -> { if (confirmNotVampire(character, humanFactory)) {minionCount++;} }
                default -> keepCreatingMinions = false;
            }
        }

        if (minionCount == 5) {
            System.out.println("\nSe ha alcanzado el límite máximo de 5 esbirros para " + character.getName() + ".");
        }

        showMinions(character.getAbstractMinions());
    }

    private boolean confirmNotVampire(AbstractCharacter character, HumanFactory humanFactory) {
        if (character.getClass().getSimpleName().equals("Vampire")) {
            System.out.println("Los personajes de tipo vampiro no pueden tener esbirros humanos, ¡se los comerían!");
            return false;
        } else {
            humanFactory.createProduct();
            return true;
        }
    }

    // Method to delete minions
    private void removeMinion(AbstractCharacter character, List<AbstractMinion> minions) {
        if (minions.isEmpty()) {
            System.out.println("No tienes esbirros para eliminar.");
            return;
        }

        showMinions(minions);

        System.out.println("¿Qué esbirro deseas eliminar? (Escribe su número o 0 para cancelar):");
        System.out.println("Ten en cuenta que si eliminas a un esbirro demonio, todos sus esbirros también se eliminarán");
        int option = ConsoleInput.readInt(0, minions.size());

        if (option > 0) {
            System.out.println("Has eliminado a " + minions.get(option - 1).getName() + ".");
            character.removeMinion(option - 1);
        }
    }

    // Method for displaying the minions in a tree shape
    private void showMinions(List<AbstractMinion> minions) {
        int index = 1;

        for (AbstractMinion minion : minions) {
            System.out.println(index + ". " + minion.getClass().getSimpleName() + " - " + minion.getName() + " (Salud: " + minion.getHealth() + ")");
            index++;

            // If it's a demon, we also show its minions
            if (minion instanceof Demon demon) {
                if (demon.getMinions() != null && !demon.getMinions().isEmpty()) {
                    System.out.println("\t[Esbirros de " + demon.getName() + "]:");
                    for (AbstractMinion subMinion : demon.getMinions()) {
                        System.out.println("\t  - " + subMinion.getClass().getSimpleName() + " - " + subMinion.getName());
                    }
                }
            }
        }
    }
}
