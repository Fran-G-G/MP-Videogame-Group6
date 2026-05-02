package Test;

import Game.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChallengeTest {


    @Test
    void constructorDebeCrearChallengeCorrectamente() {
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
    void constructorDebeLanzarExcepcionSiApuestaNegativa() {
        Player p1 = new Player("Alice", "Alice1", "12345678");
        Player p2 = new Player("Bob", "Bob1", "12345678");

        assertThrows(IllegalArgumentException.class, () -> {
            new Challenge(p1, p2, -5);
        });
    }

    @Test
    void acceptDebeCambiarEstadoAaccepted() {
        Player p1 = new Player("Alice", "Alice1", "12345678");
        Player p2 = new Player("Bob", "Bob1", "12345678");

        Challenge c = new Challenge(p1, p2, 50);
        c.accept();

        assertEquals(Challenge.Status.ACCEPTED, c.getStatus());
    }

    @Test
    void acceptDebeFallarSiNoEstaValidado() {
        Player p1 = new Player("Alice", "Alice1", "12345678");
        Player p2 = new Player("Bob", "Bob1", "12345678");

        Challenge c = new Challenge(p1, p2, 50);

        // Forzamos validated = false mediante reflexión o setter si existiera
        // Aquí lo hacemos con reflexión porque no hay setter
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
    void rejectDebeCambiarEstadoArejected() {
        Player p1 = new Player("Alice", "Alice1", "12345678");
        Player p2 = new Player("Bob", "Bob1", "12345678");

        Challenge c = new Challenge(p1, p2, 100);
        c.reject();

        assertEquals(Challenge.Status.REJECTED, c.getStatus());
    }
}
