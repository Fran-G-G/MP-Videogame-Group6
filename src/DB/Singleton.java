package DB;

import Game.Admin;
import Game.Player;

import java.util.ArrayList;

public class Singleton {
    private static final Singleton instance = new Singleton();
    private final Adapter adapter = new Adapter();

    private Singleton(){
    }

    /**
     * Returns the only instance of the Singleton
     * @return instance
     */
    public static Singleton getInstance(){
        return instance;
    }

    /**
     * Registers a player in the database
     * @param player
     */
    public void registerPlayer(Player player){
        adapter.registerPlayer(player);
    }

    /**
     * Deletes a player from the database
     * @param player
     */
    public void deletePlayer(Player player){
        adapter.deletePlayer(player);
    }

    /**
     * Registers an administrator in the database
     * @param admin
     */
    public void registerAdmin(Admin admin){
        adapter.registerAdmin(admin);
    }

    /**
     * Deletes an administrator from the database
     * @param admin
     */
    public void deleteAdmin(Admin admin){
        adapter.deleteAdmin(admin);
    }

    /**
     * Loads a player from the database
     * @param nick
     * @param password
     * @return player
     */
    public Player loadPlayer(String nick, String password){
        return adapter.loadPlayer(nick, password);
    }

    /**
     * Loads an administrator from the database
     * @param nick
     * @param password
     * @return admin
     */
    public Admin loadAdmin(String nick, String password) {
        return adapter.loadAdmin(nick, password);
    }

    /**
     * Finds a player by its nick in the database
     * @param nick
     * @return player
     */
    public Player findPlayerByNick(String nick){
        return adapter.findPlayerByNick(nick);
    }

    /**
     * Updates the database with the current players data
     */
    public void updatePlayersDB(){
        adapter.updatePlayersDB();
    }

    /**
     * Updates the database with the current administrators data
     */
    public void updateAdminsDB(){
        adapter.updateAdminsDB();
    }

    /**
     * Updates the player ranking and returns it
     * @return ranking
     */
    public ArrayList<Player> updateRanking(){
        return adapter.updateRanking();
    }
}
