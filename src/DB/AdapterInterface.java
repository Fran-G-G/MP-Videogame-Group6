package DB;

import Game.Player;

import java.util.ArrayList;

public interface AdapterInterface {
    public void registerPlayer(Player player);
    public Player findPlayerByNick(String nick);
    public Player loadPlayer(String nick, String password);
    public void updatePlayersDB();
    public ArrayList<Player> updateRanking();
}
