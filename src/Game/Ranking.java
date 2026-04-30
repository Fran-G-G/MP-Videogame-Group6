package Game;

import DB.Singleton;

import java.util.ArrayList;

public class Ranking {

    ArrayList<Player> ranking = new ArrayList<>();

    public void showRanking() {
        ranking = Singleton.getInstance().updateRanking();

        System.out.println("-----------------------------------------------------------------------------\n");
        System.out.println("Top 10 de los jugadores con más oro \n");
        for (int i = 0; i < Math.min(10, ranking.size()); i++) {
            Player player = ranking.get(i);
            System.out.println(i+1 + ". " + "Nick: " + player.getNick() + ", Oro: " + player.getCharacter().getGold());
        }
    }
}
