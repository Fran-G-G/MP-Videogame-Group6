package Test;

import DB.DBManager;
import Game.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogInTest {

    @Test
    void loginFailsWhenPlayerIsBlocked() {
        DBManager testDB = new DBManager("./config/testPlayers.dat", "./config/testAdmins.dat");

        Player player = new Player("Alice", "Alice1", "12345678");
        player.block();

        testDB.registerPlayer(player);
        Player loaded = testDB.loadPlayer("Alice1", "12345678");

        assertEquals(loaded.isBlocked(), true);

        player.unblock();
        loaded = testDB.loadPlayer("Alice1", "12345678");

        assertEquals(loaded.isBlocked(), false);
    }
}
