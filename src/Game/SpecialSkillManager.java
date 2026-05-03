package Game;

import java.util.Random;

public class SpecialSkillManager {

    public void manageSpecialSkills(AbstractCharacter character) {

        // We use the switch to identify the specific class automatically
        switch (character) {
            case Vampire v  -> createDiscipline(v);
            case Werewolf w -> createGift(w);
            case Hunter h   -> createTalent(h);
            case null       -> System.out.println("El personaje es nulo.");
            default         -> System.out.println("Tipo de personaje no reconocido.");
        }
    }

    private void createDiscipline(Vampire vampire) {
        Random random = new Random();

        String name = ConsoleInput.readString("Nombre de la disciplina del vampiro: ");
        int attack = random.nextInt(3) + 1;
        int defense = random.nextInt(3) + 1;
        int bloodCost = random.nextInt(3) + 1;

        Discipline discipline = new Discipline(name, attack, defense, bloodCost);
        vampire.setSkill(discipline);
    }

    private void createGift(Werewolf werewolf) {
        Random random = new Random();

        String name = ConsoleInput.readString("Nombre del don del hombre lobo: ");
        int attack = random.nextInt(3) + 1;
        int defense = random.nextInt(3) + 1;
        int rageCost = random.nextInt(3) + 1;

        Gift gift = new Gift(name, attack, defense, rageCost);
        werewolf.setSkill(gift);
    }

    private void createTalent(Hunter hunter) {
        Random random = new Random();

        String name = ConsoleInput.readString("Nombre del talento del cazador: ");
        int attack = random.nextInt(3) + 1;
        int defense = random.nextInt(3) + 1;

        Talent talent = new Talent(name, attack, defense);
        hunter.setSkill(talent);
    }
}
