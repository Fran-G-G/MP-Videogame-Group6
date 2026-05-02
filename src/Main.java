import Game.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class Main {
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("  🧛‍♂️ PELEAS EN ULLEWAND 🐺");
        System.out.println("=================================\n");

        GameStarter gameStarter = new GameStarter();
        gameStarter.run();
    }

    static class AbstractCharacterTest {

        private Vampire vampire;
        private Ghoul ghoul;
        private Demon demon;
        private Human human;

        @BeforeEach
        void setUp() {
            vampire = new Vampire("V", 5, 3, 100);
            ghoul = new Ghoul("Ghoul", 2, 3);
            human = new Human("Human", 1, Human.Loyalty.HIGH);
            demon = new Demon("Demon", 3, "Pacto");
            demon.addMinion(human);
        }

        @Test
        void addMinion_shouldAddToList() {
            vampire.addMinion(ghoul);
            assertEquals(2, vampire.getTotalMinionHealth()); // health 2
        }

        @Test
        void getTotalMinionHealth_shouldSumAllRecursively() {
            vampire.addMinion(ghoul); // 2
            vampire.addMinion(demon); // 3 + 1 = 4
            assertEquals(6, vampire.getTotalMinionHealth());
        }

        @Test
        void initMinionHealthPool_shouldSetPoolToTotal() {
            vampire.addMinion(ghoul);
            vampire.initMinionHealthPool();
            assertEquals(2, vampire.getMinionHealthPool());
        }

        @Test
        void applyDamageToMinions_withinPool_shouldReturnZeroOverflow() {
            vampire.addMinion(ghoul);
            vampire.initMinionHealthPool();
            int overflow = vampire.applyDamageToMinions(1);
            assertEquals(0, overflow);
            assertEquals(1, vampire.getMinionHealthPool());
        }

        @Test
        void applyDamageToMinions_exceedingPool_shouldReturnOverflow() {
            vampire.addMinion(ghoul);
            vampire.initMinionHealthPool();
            int overflow = vampire.applyDamageToMinions(3);
            assertEquals(1, overflow);
            assertEquals(0, vampire.getMinionHealthPool());
        }

        @Test
        void minionCopy_shouldBeDeep() {
            vampire.addMinion(demon);
            List<AbstractMinion> copiedList = vampire.getMinionsCopy();
            assertEquals(1, copiedList.size());
            Demon copiedDemon = (Demon) copiedList.get(0);
            assertNotSame(demon, copiedDemon);
            assertEquals(demon.getName(), copiedDemon.getName());
            // modifying original doesn't affect copy
            demon.setHealth(0);
            assertEquals(3, copiedDemon.getHealth());
        }

        @Test
        void restoreMinions_shouldReplaceListAndResetPool() {
            vampire.addMinion(ghoul);
            List<AbstractMinion> saved = vampire.getMinionsCopy();
            vampire.addMinion(new Ghoul("Extra", 1, 1));
            vampire.restoreMinions(saved);
            assertEquals(2, vampire.getTotalMinionHealth());
            assertEquals(2, vampire.getMinionHealthPool()); // reinitialized
        }

        @Test
        void strengthsAndWeaknesses_shouldModifyPotential() {
            vampire.addStrength(new Strength("Fuerza", 3));
            vampire.addWeakness(new Weakness("Debilidad", 1));
            // attack potential without skill: power(3) + strength(3) - weakness(1) = 5
            assertEquals(7, vampire.getAttackPotential(false));
        }
    }
}