import java.util.ArrayList;

public class Ranking {

    public void showRanking() {
        System.out.println("-----------------------------------------------------------------------------\n");
        System.out.println("Top 10 de los jugadores con más oro \n");

        ArrayList<Player> players = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            //Player p = players.get(i); // Index access
            //AbstractCharacter character = p.getCharacter();

            //System.out.print("\t" + (i + 1) + ". " + p.getNick() + ": " + character.getGold() + " oro\n");
        }
    }
}
