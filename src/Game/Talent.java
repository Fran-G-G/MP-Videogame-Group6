package Game;

import java.io.Serializable;

/**
 * Special skill used by Hunters.
 */
public class Talent extends SpecialSkill implements Serializable {

    public Talent(String name, int attack, int defense) {
        super(name, attack, defense);
    }
}