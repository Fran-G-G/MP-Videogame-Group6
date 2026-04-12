import java.util.Random;

/**
 * Werewolf character.
 */
public class Werewolf extends AbstractCharacter {

    private int rage; // Range: 0 - 3
    private boolean transformed; // Human = false, Wolf = true
    private int height;
    private int weight;
    private final int heightTransformationIncrement; // Random number that affects the height while transforming
    private final int weightTransformationIncrement; // Random number that affects the weight while transforming

    public Werewolf(String name, int health, int power, int height, int weight) {
        super(name, health, power);
        this.rage = 0;
        this.transformed = false;
        this.height = height;
        this.weight = weight;

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

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }
}