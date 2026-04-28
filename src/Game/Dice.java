package Game;

import java.util.Random;

/**
 * Utility class for dice rolling mechanics.
 * Successes are counted on rolls of 5 or 6 on a d6.
 */
public final class Dice {

    private static final Random RANDOM = new Random();

    private Dice() {
        // Utility class, no instantiation
    }

    /**
     * Rolls a number of six-sided dice and counts successes.
     * A success is a roll of 5 or 6.
     *
     * @param numberOfDice number of dice to roll
     * @return number of successes
     */
    public static int rollSuccesses(int numberOfDice) {
        int successes = 0;
        for (int i = 0; i < numberOfDice; i++) {
            int roll = RANDOM.nextInt(6) + 1;
            if (roll >= 5) {
                successes++;
            }
        }
        return successes;
    }
}