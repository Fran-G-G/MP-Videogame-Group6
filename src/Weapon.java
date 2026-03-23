/**
 * Represents a weapon.
 */
public class Weapon extends Equipment {

    private int hands; // 1 or 2

    public Weapon(String name, int attackModifier, int defenseModifier, int hands) {
        super(name, attackModifier, defenseModifier);

        if (hands != 1 && hands != 2) {
            throw new IllegalArgumentException("El arma debe ser de 1 o 2 manos");
        }

        this.hands = hands;
    }

    public int getHands() {
        return hands;
    }
}