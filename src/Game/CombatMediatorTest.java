package Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class CombatMediatorTest {

    private Player attacker, defender;
    private CombatMediator mediator;

    @BeforeEach
    void setUp() {
        attacker = new Player("Att", "att", "12345678");
        Vampire v = new Vampire("V", 5, 3, 100);
        v.setSkill(new Discipline("D", 2, 1, 2));
        attacker.setCharacter(v);
        v.initMinionHealthPool();

        defender = new Player("Def", "def", "12345678");
        Werewolf w = new Werewolf("W", 5, 4, 1.8, 80);
        w.setSkill(new Gift("G", 2, 1, 1));
        defender.setCharacter(w);
        w.initMinionHealthPool();

        mediator = new CombatMediator(attacker, defender);
    }

    @Test
    void shouldUseSkill_vampireWithEnoughBlood_shouldReturnTrue() {
        assertTrue(mediator.shouldUseSkill(attacker.getCharacter()));
    }

    @Test
    void shouldUseSkill_vampireWithoutBlood_shouldReturnFalse() {
        Vampire v = (Vampire) attacker.getCharacter();
        for (int i = 0; i < 5; i++) v.useDisciplineInCombat();
        assertFalse(mediator.shouldUseSkill(v));
    }

    @Test
    void applyDamage_deductsMinionPoolFirst() {
        defender.getCharacter().addMinion(new Ghoul("G", 2, 1));
        defender.getCharacter().initMinionHealthPool();
        mediator.applyDamage(defender);
        assertEquals(1, defender.getCharacter().getMinionHealthPool());
        assertEquals(5, defender.getCharacter().getHealth()); // no health lost
    }

    @Test
    void applyDamage_overflowsToHealthWhenPoolEmpty() {
        mediator.applyDamage(defender);
        assertEquals(4, defender.getCharacter().getHealth());
    }

    @Test
    void executeTurn_shouldNotCrashAndKeepValidHealth() {
        // Redirect output to avoid console clutter
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        mediator.executeTurn(attacker, defender);
        System.setOut(originalOut);
        assertTrue(attacker.getCharacter().getHealth() >= 0);
        assertTrue(defender.getCharacter().getHealth() >= 0);
    }

    @Test
    void combat_shouldFinishWithARestoredState() {
        // Redirect input first
        String input = " \n" + "\n".repeat(1000);
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // NOW create the mediator with the redirected input
        CombatMediator mediator = new CombatMediator(attacker, defender);

        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        mediator.start(10);
        System.setOut(originalOut);

        assertEquals(5, attacker.getCharacter().getHealth());
        assertEquals(5, defender.getCharacter().getHealth());
    }
}