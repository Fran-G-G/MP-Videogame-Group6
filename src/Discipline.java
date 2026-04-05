/**
 * Special skill used by Vampires.
 */
public class Discipline extends SpecialSkill {

    private final int bloodCost; // Range: 1 - 3

    public Discipline(String name, int attack, int defense, int bloodCost) {
        super(name, attack, defense);

        if (bloodCost < 1 || bloodCost > 3) {
            throw new IllegalArgumentException("El coste de sangre debe estar entre 1 y 3");
        }

        this.bloodCost = bloodCost;
    }

    public int getBloodCost() {
        return bloodCost;
    }
}