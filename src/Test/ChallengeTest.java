package Test;

import Game.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChallengeTest {


    @Test
    void constructorCreatesChallengeCorrectly() {
        Player p1 = new Player("Alice", "Alice1", "12345678");
        Player p2 = new Player("Bob", "Bob1", "12345678");

        Challenge c = new Challenge(p1, p2, 100);

        assertEquals(p1, c.getChallenger());
        assertEquals(p2, c.getChallenged());
        assertEquals(100, c.getGoldBet());
        assertEquals(Challenge.Status.PENDING, c.getStatus());
        assertTrue(c.isValidated());
    }

    @Test
    void constructorThrowsExceptionIfNegativeBet() {
        Player p1 = new Player("Alice", "Alice1", "12345678");
        Player p2 = new Player("Bob", "Bob1", "12345678");

        assertThrows(IllegalArgumentException.class, () -> {
            new Challenge(p1, p2, -5);
        });
    }

    @Test
    void acceptChangesStateToAccepted() {
        Player p1 = new Player("Alice", "Alice1", "12345678");
        Player p2 = new Player("Bob", "Bob1", "12345678");

        Challenge c = new Challenge(p1, p2, 50);
        c.accept();

        assertEquals(Challenge.Status.ACCEPTED, c.getStatus());
    }

    @Test
    void acceptFailsIfNotValidated() {
        Player p1 = new Player("Alice", "Alice1", "12345678");
        Player p2 = new Player("Bob", "Bob1", "12345678");

        Challenge c = new Challenge(p1, p2, 50);

        // Set validated = false using reflection or setter if exists
        // Here we use reflection because there is no setter
        try {
            var field = Challenge.class.getDeclaredField("validated");
            field.setAccessible(true);
            field.set(c, false);
        } catch (Exception e) {
            fail("Error manipulando el campo validated para el test");
        }

        assertThrows(IllegalStateException.class, c::accept);
    }

    @Test
    void rejectChangesStateToRejected() {
        Player p1 = new Player("Alice", "Alice1", "12345678");
        Player p2 = new Player("Bob", "Bob1", "12345678");

        Challenge c = new Challenge(p1, p2, 100);
        c.reject();

        assertEquals(Challenge.Status.REJECTED, c.getStatus());
    }
}
