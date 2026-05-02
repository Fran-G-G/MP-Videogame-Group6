package Test;

import Game.*;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void calculateRejectionPenalty_debeCalcularEl10PorCientoRedondeado() {
        assertEquals(10, Calculator.calculateRejectionPenalty(100));
        assertEquals(5, Calculator.calculateRejectionPenalty(50));
        assertEquals(0, Calculator.calculateRejectionPenalty(1)); // 0.1 → 0
    }

    @Test
    void calculateWinnerGoldGain_debeDuplicarLaApuesta() {
        assertEquals(200, Calculator.calculateWinnerGoldGain(100));
        assertEquals(0, Calculator.calculateWinnerGoldGain(0));
    }

    // ---------- calculateAttackMod con personajes reales ----------

    @Test
    void calculateAttackMod_vampire_conSkillEquipoFortalezasYDebilidadesYBonusDeSangre() {
        Vampire v = new Vampire("Dracula", 5, 3, 500);

        // skill: +2 ataque
        SpecialSkill skill = new SpecialSkill("Disciplina básica", 2, 1) {};
        v.setSkill(skill);

        // armas y armadura
        Weapon espada = new Weapon("Espada", 3, 0, 1);
        Weapon daga = new Weapon("Daga", 1, 0, 1);
        Armour armadura = new Armour("Armadura ligera", 1, 1);

        v.chooseActiveEquipment(
                List.of(espada, daga),
                List.of(armadura),
                List.of(espada, daga),
                armadura
        );

        // fortalezas y debilidades
        v.addStrength(new Strength("Fuerza sobrenatural", 2));
        v.addWeakness(new Weakness("Luz solar", 1));

        // blood por defecto = 10 → bonus +2
        int esperado =
                v.getPower()      // 3
                        + skill.getAttackValue() // 2
                        + espada.getAttackModifier() // 3
                        + daga.getAttackModifier()   // 1
                        + armadura.getAttackModifier() // 1
                        + 2 // bonus sangre (>=5)
                        + 2 // strength
                        - 1; // weakness

        int resultado = Calculator.calculateAttackMod(v);
        assertEquals(esperado, resultado);
    }

    @Test
    void calculateAttackMod_werewolf_conRageComoBonus() {
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
    void calculateAttackMod_hunter_conVoluntadComoBonus() {
        Hunter h = new Hunter("Cazador", 5, 3);

        // sin skill, sin equipo, sin fortalezas/debilidades
        // willpower por defecto = 3
        int esperado = h.getPower() + h.getWill(); // 3 + 3 = 6

        int resultado = Calculator.calculateAttackMod(h);
        assertEquals(esperado, resultado);
    }

    @Test
    void calculateAttackMod_hunter_conFortalezasYDebilidadesYSinVoluntad() {
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
