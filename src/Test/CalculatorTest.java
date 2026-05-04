package Test;

import Game.*;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void calculateRejectionPenalty_mustCalculateTheRounded10Percent() {
        assertEquals(10, Calculator.calculateRejectionPenalty(100));
        assertEquals(5, Calculator.calculateRejectionPenalty(50));
        assertEquals(0, Calculator.calculateRejectionPenalty(1)); // 0.1 → 0
    }

    @Test
    void calculateWinnerGoldGain_mustDoubleTheBet() {
        assertEquals(200, Calculator.calculateWinnerGoldGain(100));
        assertEquals(0, Calculator.calculateWinnerGoldGain(0));
    }

    // ---------- calculateAttackMod with real characters ----------

    @Test
    void calculateAttackMod_vampire_withTeamSkillStrengthsWeaknessesAndBloodBonus() {
        Vampire v = new Vampire("Dracula", 5, 3, 500);

        // skill: +2 attack
        SpecialSkill skill = new SpecialSkill("Disciplina básica", 2, 1) {};
        v.setSkill(skill);

        // weapon
        Weapon espada = new Weapon("Espada", 3, 0, 1);
        Weapon daga = new Weapon("Daga", 1, 0, 1);
        Armour armadura = new Armour("Armadura ligera", 1, 1);

        v.chooseActiveEquipment(
                List.of(espada, daga),
                List.of(armadura),
                List.of(espada, daga),
                armadura
        );

        // Strength and weakness
        v.addStrength(new Strength("Fuerza sobrenatural", 2));
        v.addWeakness(new Weakness("Luz solar", 1));

        // blood = 10 → bonus +2
        int esperado =
                v.getPower()      // 3
                        + skill.getAttackValue() // 2
                        + espada.getAttackModifier() // 3
                        + daga.getAttackModifier()   // 1
                        + armadura.getAttackModifier() // 1
                        + 2 // bonus blood (>=5)
                        + 2 // strength
                        - 1; // weakness

        int resultado = Calculator.calculateAttackMod(v);
        assertEquals(esperado, resultado);
    }

    @Test
    void calculateAttackMod_werewolf_withRageBonus() {
        Werewolf w = new Werewolf("Lobo", 5, 4, 1.80, 80.0);

        // sin skill, sin equipo, sin fortalezas/debilidades
        // rage empieza en 0, lo subimos a 3
        w.increaseRageOnDamage();
        w.increaseRageOnDamage();
        w.increaseRageOnDamage(); // rage = 3 (máx)

        int esperado = w.getPower() + w.getRage(); // 4 + 3 = 7
        int resultado = Calculator.calculateAttackMod(w);

        assertEquals(esperado, resultado);
    }

    @Test
    void calculateAttackMod_hunter_withVoluntadBonus() {
        Hunter h = new Hunter("Cazador", 5, 3);

        // sin skill, sin equipo, sin fortalezas/debilidades
        // willpower por defecto = 3
        int esperado = h.getPower() + h.getWill(); // 3 + 3 = 6

        int resultado = Calculator.calculateAttackMod(h);
        assertEquals(esperado, resultado);
    }

    @Test
    void calculateAttackMod_hunter_withStrengthsAndWeaknessesAndWithoutWillpower() {
        Hunter h = new Hunter("Cazador", 5, 3);

        // bajamos la voluntad a 0
        h.decreaseWillpowerOnDamage();
        h.decreaseWillpowerOnDamage();
        h.decreaseWillpowerOnDamage(); // will = 0

        h.addStrength(new Strength("Entrenamiento", 2));
        h.addWeakness(new Weakness("Miedo", 4));

        // sin skill, sin equipo
        int esperado =
                h.getPower()      // 3
                        + 0               // will
                        + 2               // strength
                        - 4;              // weakness

        int resultado = Calculator.calculateAttackMod(h);
        assertEquals(esperado, resultado);
    }
}
