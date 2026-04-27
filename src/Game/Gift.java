package Game;

import java.io.Serializable;

/**
 * Special skill used by Werewolves.
 */
public class Gift extends SpecialSkill implements Serializable {

    private final int rageCost;

    public Gift(String name, int attack, int defense, int rageCost) {
        super(name, attack, defense);
        this.rageCost = rageCost;
    }

    public int getRageCost() {
        return rageCost;
    }
}