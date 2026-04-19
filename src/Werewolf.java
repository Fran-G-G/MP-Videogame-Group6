import java.util.Random;

/**
 * Werewolf character.
 */
public class Werewolf extends AbstractCharacter {

    private int rage; // Range: 0 - 3
    private boolean transformed; // Human = false, Wolf = true
    private double height;
    private double weight;
    private final int heightTransformationIncrement; // Random number that affects the height while transforming
    private final int weightTransformationIncrement; // Random number that affects the weight while transforming

    public Werewolf(String name, int health, int power, double height, double weight) {
        super(name, health, power);
        this.rage = 0;
        this.transformed = false;
        this.height = Math.round(height * 100.0) / 100.0;
        this.weight = Math.round(weight * 100.0) / 100.0;

        Random random = new Random();
        double num = random.nextDouble() * 2.0;
        double aux = Double.parseDouble( String.format("%.2f", num) );
        this.heightTransformationIncrement = (int) Math.round( height + (0.5 + 0.5 * (aux / 2) ) );
        this.weightTransformationIncrement = (int) Math.round( weight + (90 + 10 * aux) );
    }

    public void transform() {
        transformed = !transformed;

        if (transformed) {
            height += heightTransformationIncrement;
            weight += weightTransformationIncrement;
        } else {
            height -= heightTransformationIncrement;
            weight -= weightTransformationIncrement;
        }
    }

    public void increaseRage() {
        if (rage < 3) {
            rage++;
        }
    }

    public int getRage() {
        return rage;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

}