package Test;

import Game.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MinionTest {
    Ghoul ghoul = new Ghoul("Ghoul", 2, 3);
    Human human = new Human("Human", 1, Human.Loyalty.HIGH);
    Demon demon = new Demon("Demon", 3, "Pacto");

    @Test
    void getHealth() {
        assertEquals(2, ghoul.getHealth());
        assertEquals(1, human.getHealth());
        assertEquals(3, demon.getHealth());
    }

    @Test
    void setHealth() {
        ghoul.setHealth(3);
        human.setHealth(2);
        demon.setHealth(1);
        assertEquals(3, ghoul.getHealth());
        assertEquals(2, human.getHealth());
        assertEquals(1, demon.getHealth());
    }

    @Test
    void getName() {
        assertEquals("Ghoul", ghoul.getName());
        assertEquals("Human", human.getName());
        assertEquals("Demon", demon.getName());
    }

    @Test
    void copy() {
        Ghoul ghoulCopy = (Ghoul) ghoul.copy();
        Human humanCopy = (Human) human.copy();
        Demon demonCopy = (Demon) demon.copy();

        assertNotEquals(ghoulCopy, ghoul);
        assertEquals(ghoulCopy.getName(), ghoul.getName());
        assertEquals(ghoulCopy.getHealth(), ghoul.getHealth());
        assertEquals(ghoulCopy.getDependency(), ghoul.getDependency());
        assertEquals(ghoulCopy.getTypeName(), ghoul.getTypeName());

        assertNotEquals(humanCopy, human);
        assertEquals(humanCopy.getName(), human.getName());
        assertEquals(humanCopy.getHealth(), human.getHealth());
        assertEquals(humanCopy.getTypeName(), human.getTypeName());

        assertNotEquals(demonCopy, demon);
        assertEquals(demonCopy.getName(), demon.getName());
        assertEquals(demonCopy.getHealth(), demon.getHealth());
        assertEquals(demonCopy.getTypeName(), demon.getTypeName());
    }

    @Test
    void getTypeName() {
        assertEquals("Ghoul", ghoul.getName());
        assertEquals("Human", human.getName());
        assertEquals("Demon", demon.getName());
    }

}