package Game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Class to read the files that contain information about the strengths and weaknesses.
 */
public class ConfigReader {

    private static final String FOLDER = "config/";

    // Use generic <T> so that it works for both Strength and Weakness
    public static <T> ArrayList<T> loadAttributes(String characterType, String category, BiFunction<String, Integer, T> builderInvoker) {
        String fileName = FOLDER + characterType + "_" + category + ".txt";
        Path path = Paths.get(fileName);
        ArrayList<T> objectList = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                // Clean ';' and blank spaces
                line = line.replace(";", "").trim();

                // If the line is empty (for example, a line break at the end), skip it
                if (line.isEmpty()) continue;

                // Divide where the comma is
                String[] partes = line.split(",");

                if (partes.length == 2) {
                    String name = partes[0].trim();

                    try {
                        // Change the second part (value) into an int
                        int value = Integer.parseInt(partes[1].trim());

                        // Create the object using the parameter constructor
                        T newObject = builderInvoker.apply(name, value);
                        objectList.add(newObject);

                    } catch (NumberFormatException e) {
                        System.err.println("Error: El valor no es un número válido en la línea: " + line);
                    }
                } else {
                    System.err.println("Error: Formato incorrecto en la línea: " + line);
                }
            }

        } catch (IOException e) {
            System.err.println("No se pudo leer el archivo: " + fileName);
        }

        return objectList;
    }
}