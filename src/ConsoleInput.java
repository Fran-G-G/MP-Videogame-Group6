import java.util.Scanner;

public class ConsoleInput {
    // Define a single static Scanner for the entire application.
    // We NEVER shut it down, so as not to kill the flow of System.in.
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Reads an integer, ensuring that it is within the range.
     */
    public static int readInt(int min, int max) {
        int value;

        while (true) {
            try {
                System.out.print("Opción (" + min + "-" + max + "): ");
                value = Integer.parseInt(SCANNER.nextLine());

                if (value >= min && value <= max) {
                    return value;
                }

                System.out.println("Error: El número debe estar entre " + min + " y " + max + ".");

            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, introduce un número válido.");
            }
        }
    }

    /**
     * Reads a decimal number (double) ensuring that it is within the range.
     */
    public static double readDouble(double min, double max) {
        double value;

        while (true) {
            try {
                System.out.print("Introduce un valor decimal (" + min + "-" + max + "): ");
                // Replace the comma with a period in case the user uses other regional format
                String input = SCANNER.nextLine().replace(',', '.');
                value = Double.parseDouble(input);

                if (value >= min && value <= max) {
                    return value;
                }

                System.out.println("Error: El valor debe estar entre " + min + " y " + max + ".");

            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, introduce un número decimal válido (ej: 1.85).");
            }
        }
    }

    /**
     * Reads a non-empty string.
     */
    public static String readString(String prompt) {
        String input;

        while (true) {
            System.out.print(prompt);
            input = SCANNER.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Error: El campo no puede estar vacío.");
        }
    }

    /**
     * Asks a Yes/No question and returns a boolean.
     */
    public static boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt + " (s/n): ");
            String input = SCANNER.nextLine().trim().toLowerCase();

            if (input.equals("s") || input.equals("si") || input.equals("sí")) {
                return true;
            }
            if (input.equals("n") || input.equals("no")) {
                return false;
            }

            System.out.println("Error: Por favor, responde con 's' para Sí o 'n' para No.");
        }
    }
}
