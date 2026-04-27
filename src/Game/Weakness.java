package Game;

import java.io.Serializable;

/**
 * Represents a weakness of a character.
 */
public class Weakness extends Characteristic implements Serializable {

    public Weakness(String name, int value) {
        super(name, value);
    }
}