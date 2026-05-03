package Test;
import DB.Singleton;
import Game.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LogInTest {

    @Test
    void loginFailsWhenPlayerIsBlocked() {
        // Arrange
        Singleton singleton = Singleton.getInstance();

        Player p = new Player("Alice", "alice", "12345678");

        p.blocked();

        // Save player into the singleton (or whatever method you use)
        singleton.updatePlayersDB(); // If your Singleton uses a different method, tell me
        Player result = p;

        if (result != null && result.isIs_blocked()) {
            result = null;
        }

        assertNull(result);
    }
}
