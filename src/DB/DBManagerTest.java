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
    void loadPlayers() {
        Player player = new Player("Fulano", "Fulano1", "12345678");

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new FileOutputStream("./config/players.dat")
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.writeObject(player);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DBManager db = new DBManager();
        Player cargado = db.loadPlayer(player.getNick(), player.getPassword());

        assertNotNull(cargado);
        assertEquals(player.getPassword(), cargado.getPassword());
        assertEquals(player.getNick(), cargado.getNick());
    }

    @org.junit.jupiter.api.Test
    void loadCharacter() {
        AbstractCharacter original = new Vampire("Prueba", 3, 3, 100);

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new FileOutputStream("./config/characters.dat")
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.writeObject(original);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DBManager db = new DBManager();
        AbstractCharacter cargado = db.loadCharacter();

        assertNotNull(cargado);
        assertEquals(original.getName(), cargado.getName());
        assertEquals(original.getHealth(), cargado.getHealth());
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