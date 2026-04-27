package Game;

import java.io.Serializable;

/**
 * Represents an armour.
 */
public class Armour extends Equipment implements Serializable {

    public Armour(String name, int attackModifier, int defenseModifier) {
        super(name, attackModifier, defenseModifier);
    }
}