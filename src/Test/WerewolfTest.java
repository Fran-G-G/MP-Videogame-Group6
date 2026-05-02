package Test;

import Game.Gift;
import Game.Werewolf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WerewolfTest {

    private Werewolf werewolf;
    private Gift gift;

    @BeforeEach
    void setUp() {
        werewolf = new Werewolf("Lobo", 5, 4, 1.80, 80.0);
        gift = new Gift("Aullido", 2, 1, 1);
        werewolf.setSkill(gift);
    }

    @Test
    void constructor_shouldStartRageAtZero() {
        assertEquals(0, werewolf.getRage());
    }

    @Test
    void attackPotential_withoutSkill_shouldBePower() {
        assertEquals(4, werewolf.getAttackPotential(false));
    }

    @Test
    void attackPotential_withSkillAndEnoughRage_shouldAddGiftAndRage() {
        werewolf.increaseRageOnDamage(); // rage = 1
        int potential = werewolf.getAttackPotential(true);
        // power(4) + gift(2) + rage(1) = 7
        assertEquals(7, potential);
    }

    @Test
    void attackPotential_withSkillButLowRage_shouldNotAddGift() {
        // rage=0, cost=1
        assertEquals(4, werewolf.getAttackPotential(true));
    }

    @Test
    void defensePotential_withSkillAndEnoughRage_shouldIncludeGiftDefense() {
        werewolf.increaseRageOnDamage();
        assertEquals(1, werewolf.getDefensePotential(true));
    }

    @Test
    void increaseRageOnDamage_shouldNotExceedThree() {
        for (int i = 0; i < 5; i++) {
            werewolf.increaseRageOnDamage();
        }
        assertEquals(3, werewolf.getRage());
    }

    @Test
    void canUseGift_shouldReturnTrueOnlyWhenRageEnough() {
        assertFalse(werewolf.canUseGift());
        werewolf.increaseRageOnDamage(); // rage 1 >= cost 1
        assertTrue(werewolf.canUseGift());
    }

    @Test
    void reduceHealth_shouldWork() {
        werewolf.reduceHealth(2);
        assertEquals(3, werewolf.getHealth());
    }
}