package Test;

import Game.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MementoTest {

    @Test
    void memento_shouldSaveAndRestoreHealthAndMinions() {
        Player p1 = new Player("A", "nickA", "12345678");
        Player p2 = new Player("B", "nickB", "12345678");

        Vampire v = new Vampire("Vamp", 5, 3, 150);
        v.addMinion(new Ghoul("Ghoul", 2, 3));
        v.initMinionHealthPool();
        p1.setCharacter(v);

        Werewolf w = new Werewolf("Wolf", 5, 4, 1.8, 80);
        p2.setCharacter(w);

        Originator originator = new Originator(p1, p2);
        Memento memento = originator.save();

        // apply damage
        v.reduceHealth(1);
        v.applyDamageToMinions(1);
        w.reduceHealth(2);

        originator.restore(memento);

        assertEquals(5, v.getHealth());
        assertEquals(2, v.getMinionHealthPool()); // back to original
        assertEquals(5, w.getHealth());
    }
}