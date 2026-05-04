package Test;

import Game.Dice;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

    @Test
    void rollSuccesses_shouldBeBetweenZeroAndNumberOfDice() {
        int numberOfDice = 10;
        int successes = Dice.rollSuccesses(numberOfDice);
        assertTrue(successes >= 0 && successes <= numberOfDice,
                "El número de éxitos debe estar entre 0 y el número de dados");
    }

    @Test
    void rollSuccesses_withZeroDice_shouldReturnZero() {
        assertEquals(0, Dice.rollSuccesses(0));
    }

    @Test
    void rollSuccesses_neverExceedsDiceCount() {
        // Repeat to be sure
        for (int i = 0; i < 100; i++) {
            int successes = Dice.rollSuccesses(5);
            assertTrue(successes >= 0 && successes <= 5);
        }
    }
}