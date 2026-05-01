package Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HunterTest {

    private Hunter hunter;
    private Talent talent;

    @BeforeEach
    void setUp() {
        hunter = new Hunter("Artemis", 5, 3);
        talent = new Talent("Disparo certero", 2, 1);
        hunter.setSkill(talent);
    }

    @Test
    void constructor_shouldStartWillpowerAtThree() {
        assertEquals(3, hunter.getWill());
    }

    @Test
    void attackPotential_withoutSkill_shouldBePowerPlusWillpower() {
        // power(3) + willpower(3) = 6
        assertEquals(6, hunter.getAttackPotential(false));
    }

    @Test
    void attackPotential_withSkill_shouldAddTalentAttack() {
        // power(3) + talent(2) + willpower(3) = 8
        assertEquals(8, hunter.getAttackPotential(true));
    }

    @Test
    void defensePotential_withSkill_shouldIncludeTalentDefense() {
        assertEquals(1, hunter.getDefensePotential(true));
    }

    @Test
    void decreaseWillpowerOnDamage_shouldReduceWillpower() {
        hunter.decreaseWillpowerOnDamage();
        assertEquals(2, hunter.getWill());
    }

    @Test
    void decreaseWillpowerOnDamage_shouldNotGoBelowZero() {
        for (int i = 0; i < 5; i++) {
            hunter.decreaseWillpowerOnDamage();
        }
        assertEquals(0, hunter.getWill());
    }

    @Test
    void reduceHealth_shouldWork() {
        hunter.reduceHealth(2);
        assertEquals(3, hunter.getHealth());
    }
}