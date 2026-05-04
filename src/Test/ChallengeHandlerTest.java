package Test;

import Game.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChallengeHandlerTest {

    @Test
    void handler_rejectionAppliesPenalty() {
        // Create players
        Player challenger = new Player("Alice", "A", "12345678");
        Player challenged = new Player("Bob", "B", "12345678");

        Hunter c1 = new Hunter("Cazador1", 5, 3);
        Hunter c2 = new Hunter("Cazador2", 5, 3);

        challenger.setCharacter(c1);
        challenged.setCharacter(c2);

        int goldChallengerBefore = c1.getGold();
        int goldChallengedBefore = c2.getGold();

        ChallengeHandler handler = new ChallengeHandler();

        // op = false → challenge rejection
        handler.handler(challenger, challenged, false, 100);

        int penalty = Calculator.calculateRejectionPenalty(100);

        // Verify that the gold has been updated correctly
        assertEquals(goldChallengerBefore + penalty, c1.getGold());
        assertEquals(goldChallengedBefore - penalty, c2.getGold());
    }
}
