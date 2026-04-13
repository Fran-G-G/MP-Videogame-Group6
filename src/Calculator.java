public class Calculator {

    public static int calculateRejectionPenalty(int betGold) {
        // 10% de la apuesta
        return (int) Math.round(betGold * 0.1);
    }

    public static int calculateWinnerGoldGain(int betGold) {
        // El ganador gana exactamente lo apostado por el desafiante
        return 2*betGold;
    }

    public static int calculateAttackMod(AbstractCharacter character) {
        int base = character.getPower();

        // Habilidad especial
        if (character.getSpecialSkill() != null) {
            base += character.getSpecialSkill().getAttackValue();
        }

        // Equipo activo
        if (character.getActiveWeapons() != null) {
            base += character.getActiveWeapons()
                    .stream()
                    .mapToInt(w -> w.getAttackMod())
                    .sum();
        }
        if (character.getActiveArmour() != null) {
            base += character.getActiveArmour().getAttackMod();
        }

        // Recursos específicos (sangre, rabia, voluntad)
        base += getResourceAttackBonus(character);

        // Fortalezas / debilidades presentes
        base += getCharacteristicsModifier(character);

        return Math.max(base, 0);
    }

    private static int getResourceAttackBonus(AbstractCharacter character) {
        if (character instanceof Vampire v) {
            return (v.getBloodPoints() >= 5) ? 2 : 0;
        }
        if (character instanceof Werewolf w) {
            return w.getFury(); // rabia actual
        }
        if (character instanceof Hunter h) {
            return h.getWill(); // voluntad actual
        }
        return 0;
    }


}
