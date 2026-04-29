package DB;

import Game.AbstractCharacter;
import Game.Admin;
import Game.Player;
import Game.Vampire;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class DBManagerTest {

    @org.junit.jupiter.api.Test
    void registerUser() {
    }

    @org.junit.jupiter.api.Test
    void checkUser() {
    }

    @org.junit.jupiter.api.Test
    void registerCharacter() {
    }

    @org.junit.jupiter.api.Test
    void loadPlayer() {
        DBManager db = new DBManager();

        Player player = new Player("Fulano", "Fulano1", "12345678");
        Vampire vampire = new Vampire("Pepe", 2, 2, 300);
        player.setCharacter(vampire);

        db.registerPlayer(player);
        Player cargado = db.loadPlayer(player.getNick(), player.getPassword());

        assertNotNull(cargado);
        assertEquals(player.getPassword(), cargado.getPassword());
        assertEquals(player.getNick(), cargado.getNick());
        assertNotNull(cargado.getCharacter());
        assertEquals(player.getCharacter().getName(), cargado.getCharacter().getName());
        assertEquals(player.getCharacter().getHealth(), cargado.getCharacter().getHealth());
    }

    @org.junit.jupiter.api.Test
    void loadAdmin() {
        DBManager db = new DBManager();

        Admin admin = new Admin("Admin", "Admin1", "12345678");
        db.registerAdmin(admin);
        Admin cargado = db.loadAdmin(admin.getNick(), admin.getPassword());

        assertNotNull(cargado);
        assertEquals(admin.getPassword(), cargado.getPassword());
        assertEquals(admin.getNick(), cargado.getNick());
    }

    @org.junit.jupiter.api.Test
    void loadPlayers() {
        DBManager db = new DBManager();
        Player cargado = db.loadPlayer("Fulano1", "12345678");

        assertNotNull(cargado);
        assertEquals("12345678", cargado.getPassword());
        assertEquals("Fulano1", cargado.getNick());
        assertNotNull(cargado.getCharacter());
    }

    @org.junit.jupiter.api.Test
    void loadCharacter() {
//        AbstractCharacter original = new Vampire("Prueba", 3, 3, 100);
//
//        ObjectOutputStream oos = null;
//        try {
//            oos = new ObjectOutputStream(
//                    new FileOutputStream("./config/characters.dat")
//            );
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            oos.writeObject(original);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            oos.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        DBManager db = new DBManager();
//        AbstractCharacter cargado = db.loadCharacter();
//
//        assertNotNull(cargado);
//        assertEquals(original.getName(), cargado.getName());
//        assertEquals(original.getHealth(), cargado.getHealth());
    }

    @org.junit.jupiter.api.Test
    void loadCharacters() {
    }

    @org.junit.jupiter.api.Test
    void findPlayerByNick() {
    }

    @org.junit.jupiter.api.Test
    void writeData() {
    }

    @org.junit.jupiter.api.Test
    void loadData() {
    }
}