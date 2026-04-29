package Game;

import java.io.Serializable;

/**
 * Represents a strength or weakness of a character.
 */
public abstract class Characteristic implements Serializable {

    private final String name;
    private final int value; // Range: 1 - 5

    public Characteristic(String name, int value) {
        if (value < 1 || value > 5) {
            throw new IllegalArgumentException("El valor debe estar entre 1 y 5");
        }
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}