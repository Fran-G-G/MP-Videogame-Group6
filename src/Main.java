import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("  🧛‍♂️ JUEGO DE CRIATURAS 🐺");
        System.out.println("=================================\n");

        System.out.println("\n>>> INICIANDO COMBATE DE PRUEBA <<<\n");

        Player player1 = new Player("VampiroTest", "vamp", "12345678");
        Player player2 = new Player("LoboTest", "lobo", "12345678");

        // Vampire
        Vampire vampire = new Vampire("Conde Drácula", 5, 3, 150);
        vampire.setSkill(new Discipline("Sangre Ancestral", 2, 1, 2));
        Weapon vampWeapon = new Weapon("Garras Afiladas", 1, 0, 1);
        ArrayList<Weapon> vampWeapons = new ArrayList<>();
        vampWeapons.add(vampWeapon);
        vampire.chooseActiveEquipment(vampWeapons, new ArrayList<>(), vampWeapons, null);
        vampire.addMinion(new Ghoul("Esbirro Ghoul", 2, 3)); // 2 health in pool
        player1.setCharacter(vampire);

        // Werewolf
        Werewolf werewolf = new Werewolf("Lobo Feroz", 5, 4, 1.85, 85.5);
        werewolf.setSkill(new Gift("Aullido Ensordecedor", 2, 1, 1));
        Weapon wolfWeapon = new Weapon("Colmillos", 2, 0, 1);
        ArrayList<Weapon> wolfWeapons = new ArrayList<>();
        wolfWeapons.add(wolfWeapon);
        werewolf.chooseActiveEquipment(wolfWeapons, new ArrayList<>(), wolfWeapons, null);
        Demon demon = new Demon("Demonio Menor", 2, "Pacto de Sangre");
        demon.addMinion(new Human("Humano Leal", 1, Human.Loyalty.HIGH));
        werewolf.addMinion(demon); // Total minion health: 2 (demon) + 1 (human) = 3
        player2.setCharacter(werewolf);

        CombatMediator mediator = new CombatMediator(player1, player2);
        mediator.start();

        System.out.println("\n>>> FIN DEL COMBATE DE PRUEBA <<<");
    }
}