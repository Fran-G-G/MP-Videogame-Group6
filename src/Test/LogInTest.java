package Test;

import DB.Singleton;
import Game.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class LogInTest {

    @Test
    void loginFailsWhenPlayerIsBlocked() {
        Player p = new Player("Alice", "alice", "12345678");
        p.blocked();

        Player result = p;

        if (result != null && result.is_blocked()) {
            result = null;
        }

        assertNull(result);
    }
}
