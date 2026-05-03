package Game;

public class Calculator {

    public static int calculateRejectionPenalty(int betGold) {
        // 10% of the bet
        return (int) Math.round(betGold * 0.1);
    }

    public static int calculateWinnerGoldGain(int betGold) {
        // Winner receives exactly the challenger’s stake
        return 2*betGold;
    }

    public static int calculateAttackMod(AbstractCharacter character) {
        int base = character.getPower();

        // Special Skill
        if (character.getSpecialSkill() != null) {
            base += character.getSpecialSkill().getAttackValue();
        }

        // Active Equipment
        if (character.getActiveWeapons() != null) {
            base += character.getActiveWeapons().stream().mapToInt(w -> w.getAttackModifier()).sum();
        }
        if (character.getActiveArmour() != null) {
            base += character.getActiveArmour().getAttackModifier();
        }

        base = getBase(character, base);

        // Strengths / weaknesses
        base += character.getStrengthsTotalModifier();
        base -= character.getWeaknessesTotalModifier();

        return Math.max(base, 0);
    }

    private static int getBase(AbstractCharacter character, int base) {
        // blood, rage, willpower
        base += getResourceAttackBonus(character);
        return base;
    }

    private static int getResourceAttackBonus(AbstractCharacter character) {
        if (character instanceof Vampire v) {
            return (v.getBloodPoints() >= 5) ? 2 : 0;
        }
        if (character instanceof Werewolf w) {
            return w.getRage(); // rage
        }
        if (character instanceof Hunter h) {
            return h.getWill(); // willpower
        }
        return 0;
    }


}
