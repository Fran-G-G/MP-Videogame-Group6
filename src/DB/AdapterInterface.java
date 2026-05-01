package DB;

import Game.Admin;
import Game.Player;

import java.util.ArrayList;

public interface AdapterInterface {
    public void registerPlayer(Player player);
    public void deletePlayer(Player player);
    public void registerAdmin(Admin admin);
    public Player findPlayerByNick(String nick);
    public Player loadPlayer(String nick, String password);
    public Admin loadAdmin(String nick, String password);
    public void updatePlayersDB();
    public ArrayList<Player> updateRanking();
}
