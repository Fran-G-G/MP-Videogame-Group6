package Game;

import java.io.Serializable;

/**
 * Represents a strength of a character.
 */
public class Strength extends Characteristic implements Serializable {

    public Strength(String name, int value) {
        super(name, value);
    }
}