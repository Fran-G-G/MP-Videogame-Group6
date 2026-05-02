package Test;

import DB.DBManager;
import Game.Admin;
import Game.Player;
import Game.Vampire;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DBManagerTest {

    private String testPlayersFile = "./config/testPlayers.dat";
    private String testAdminsFile = "./config/testAdmins.dat";

    @org.junit.jupiter.api.BeforeAll
    static void cleanFiles(){
        try {
            Files.deleteIfExists(Path.of("./config/testPlayers.dat"));
            Files.deleteIfExists(Path.of("./config/testAdmins.dat"));
        } catch (Exception e) {
            System.err.println("No se pudieron borrar los archivos: " + e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void registerAndLoadPlayer() {
        DBManager testDB = new DBManager(testPlayersFile, testAdminsFile);

        Player player = new Player("Prueba", "Prueba1", "12345678");
        testDB.registerPlayer(player);
        Player loaded = testDB.loadPlayer("Prueba1", "12345678");

        assertNotNull(loaded);
        assertEquals(player.getNick(), loaded.getNick());
        assertEquals(player.getPassword(), loaded.getPassword());
    }

    @org.junit.jupiter.api.Test
    void registerAndLoadPlayerWithCharacter() {
        DBManager testDB = new DBManager(testPlayersFile, testAdminsFile);

        Player player = new Player("Fulano", "Fulano1", "12345678");
        Vampire vampire = new Vampire("Pepe", 2, 2, 300);
        player.setCharacter(vampire);
        testDB.registerPlayer(player);

        Player loaded = testDB.loadPlayer(player.getNick(), player.getPassword());

        assertNotNull(loaded);
        assertEquals(player.getNick(), loaded.getNick());
        assertEquals(player.getPassword(), loaded.getPassword());
        assertNotNull(loaded.getCharacter());
        assertEquals(player.getCharacter().getName(), loaded.getCharacter().getName());
        assertEquals(player.getCharacter().getHealth(), loaded.getCharacter().getHealth());
    }

    @org.junit.jupiter.api.Test
    void registerAndLoadAdmin() {
        DBManager testDB = new DBManager(testPlayersFile, testAdminsFile);

        Admin admin = new Admin("TestAdmin", "TestAdmin1", "12345678");
        testDB.registerAdmin(admin);
        Admin loaded = testDB.loadAdmin("TestAdmin1", "12345678");

        assertNotNull(loaded);
        assertEquals(admin.getNick(), loaded.getNick());
        assertEquals(admin.getPassword(), loaded.getPassword());
    }

    @org.junit.jupiter.api.Test
    void findPlayerByNick() {
        DBManager testDB = new DBManager(testPlayersFile, testAdminsFile);

        Player player = new Player("Prueba", "Prueba2", "12345678");
        testDB.registerPlayer(player);
        Player loaded = testDB.findPlayerByNick("Prueba2");

        assertNotNull(loaded);
        assertEquals(player.getNick(), loaded.getNick());
        assertEquals(player.getPassword(), loaded.getPassword());
    }

    @org.junit.jupiter.api.Test
    void deletePlayer() {
        DBManager testDB = new DBManager(testPlayersFile, testAdminsFile);

        Player player = new Player("Prueba", "Prueba3", "12345678");
        testDB.registerPlayer(player);

        testDB.deletePlayer(player);

        Player loaded = testDB.loadPlayer("Prueba3", "12345678");
        assertNull(loaded);
    }

    @org.junit.jupiter.api.Test
    void loadPlayers() {
        DBManager testDB = new DBManager(testPlayersFile, testAdminsFile);
        Player player = new Player("Prueba", "Prueba4", "12345678");
        testDB.registerPlayer(player);

        DBManager testDB2 = new DBManager(testPlayersFile, testAdminsFile);
        Player loaded = testDB2.loadPlayer("Prueba4", "12345678");

        assertNotNull(loaded);
        assertEquals(player.getNick(), loaded.getNick());
        assertEquals(player.getPassword(), loaded.getPassword());
    }

    @org.junit.jupiter.api.Test
    void loadAdmins() {
        DBManager testDB = new DBManager(testPlayersFile, testAdminsFile);
        Admin admin = new Admin("TestAdmin", "TestAdmin2", "12345678");
        testDB.registerAdmin(admin);

        DBManager testDB2 = new DBManager(testPlayersFile, testAdminsFile);
        Admin loaded = testDB2.loadAdmin("TestAdmin2", "12345678");

        assertNotNull(loaded);
        assertEquals(admin.getNick(), loaded.getNick());
        assertEquals(admin.getPassword(), loaded.getPassword());
    }

    @org.junit.jupiter.api.Test
    void updatePlayersDB() {
        DBManager testDB = new DBManager(testPlayersFile, testAdminsFile);

        Player player = new Player("Prueba", "Prueba5", "12345678");
        testDB.registerPlayer(player);

        DBManager testDB2 = new DBManager(testPlayersFile, testAdminsFile);
        Player loaded = testDB2.loadPlayer("Prueba5", "12345678");

        assertNotNull(loaded);
        assertEquals(player.getNick(), loaded.getNick());
        assertEquals(player.getPassword(), loaded.getPassword());
    }

    @org.junit.jupiter.api.Test
    void updateAdminsDB() {
        DBManager testDB = new DBManager(testPlayersFile, testAdminsFile);

        Admin admin = new Admin("TestAdmin", "TestAdmin3", "12345678");
        testDB.registerAdmin(admin);

        DBManager testDB2 = new DBManager(testPlayersFile, testAdminsFile);
        Admin loaded = testDB2.loadAdmin("TestAdmin3", "12345678");

        assertNotNull(loaded);
        assertEquals(admin.getNick(), loaded.getNick());
        assertEquals(admin.getPassword(), loaded.getPassword());
    }

    @org.junit.jupiter.api.Test
    void updateRanking() {
    }
}