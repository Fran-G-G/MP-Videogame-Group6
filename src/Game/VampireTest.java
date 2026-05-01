package Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VampireTest {

    private Vampire vampire;
    private Discipline discipline;

    @BeforeEach
    void setUp() {
        vampire = new Vampire("Drácula", 5, 3, 200);
        discipline = new Discipline("Sangre helada", 2, 1, 2);
        vampire.setSkill(discipline);
    }

    @Test
    void constructor_shouldSetFieldsCorrectly() {
        assertEquals("Drácula", vampire.getName());
        assertEquals(5, vampire.getHealth());
        assertEquals(3, vampire.getPower());
        assertEquals(200, vampire.getAge());
        assertEquals(10, vampire.getBloodPoints());
    }

    @Test
    void constructor_shouldThrowException_whenHealthOutOfRange() {
        assertThrows(IllegalArgumentException.class, () -> new Vampire("V", -1, 3, 100));
        assertThrows(IllegalArgumentException.class, () -> new Vampire("V", 6, 3, 100));
    }

    @Test
    void constructor_shouldThrowException_whenPowerOutOfRange() {
        assertThrows(IllegalArgumentException.class, () -> new Vampire("V", 5, 0, 100));
        assertThrows(IllegalArgumentException.class, () -> new Vampire("V", 5, 6, 100));
    }

    @Test
    void attackPotential_withoutSkill_shouldBePower() {
        assertEquals(5, vampire.getAttackPotential(false));
    }

    @Test
    void attackPotential_withSkillAndEnoughBlood_shouldAddDisciplineAndBonus() {
        // power(3) + discipline(2) + blood>=5 bonus(2) = 7
        assertEquals(7, vampire.getAttackPotential(true));
    }

    @Test
    void attackPotential_withSkillButLowBlood_shouldNotAddDisciplineOrBonus() {
        // drain blood to 0
        for (int i = 0; i < 5; i++) {
            vampire.useDisciplineInCombat();
        }
        assertEquals(0, vampire.getBloodPoints());
        assertEquals(3, vampire.getAttackPotential(true));
    }

    @Test
    void defensePotential_withoutSkill_shouldBeZero() {
        assertEquals(0, vampire.getDefensePotential(false));
    }

    @Test
    void defensePotential_withSkillAndEnoughBlood_shouldIncludeDefense() {
        assertEquals(1, vampire.getDefensePotential(true));
    }

    @Test
    void useDisciplineInCombat_shouldDecreaseBlood() {
        assertTrue(vampire.useDisciplineInCombat());
        assertEquals(8, vampire.getBloodPoints());
    }

    @Test
    void useDisciplineInCombat_whenNotEnoughBlood_shouldReturnFalse() {
        for (int i = 0; i < 5; i++) {
            vampire.useDisciplineInCombat();
        }
        int bloodBefore = vampire.getBloodPoints();
        assertFalse(vampire.useDisciplineInCombat());
        assertEquals(bloodBefore, vampire.getBloodPoints());
    }

    @Test
    void recoverBlood_shouldIncreaseBloodUpToTen() {
        vampire.useDisciplineInCombat(); // 8
        vampire.recoverBlood();
        assertEquals(10, vampire.getBloodPoints());
    }

    @Test
    void reduceHealth_shouldWork() {
        vampire.reduceHealth(2);
        assertEquals(3, vampire.getHealth());
    }
}