package DB;

import Game.AbstractCharacter;
import Game.Player;

import java.util.ArrayList;

public class Singleton {
    private static final Singleton instance = new Singleton();
    private final Adapter adapter = new Adapter();

    private Singleton(){
    }

    public static Singleton getInstance(){
        return instance;
    }

    public void registerPlayer(Player player){
        adapter.registerPlayer(player);
    }

    public void deletePlayer(Player player){
        adapter.deletePlayer(player);
    }

    public Player loadPlayer(String nick, String password){
        return adapter.loadPlayer(nick, password);
    }

    public Player findPlayerByNick(String nick){
        return adapter.findPlayerByNick(nick);
    }

    public void updatePlayersDB(){
        adapter.updatePlayersDB();
    }

    public ArrayList<Player> updateRanking(){
        return adapter.updateRanking();
    }
}
