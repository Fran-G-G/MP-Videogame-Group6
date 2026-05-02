package DB;

import Game.Admin;
import Game.Player;

import java.util.ArrayList;

public interface AdapterInterface {
    /**
     * Registers a player in the database
     * @param player
     */
    public void registerPlayer(Player player);

    /**
     * Deletes a player from the database
     * @param player
     */
    public void deletePlayer(Player player);

    /**
     * Registers an administrator in the database
     * @param admin
     */
    public void registerAdmin(Admin admin);

    /**
     * Deletes an administrator from the database
     * @param admin
     */
    public void deleteAdmin(Admin admin);

    /**
     * Loads a player from the database
     * @param nick
     * @param password
     * @return player
     */
    public Player loadPlayer(String nick, String password);

    /**
     * Loads an administrator from the database
     * @param nick
     * @param password
     * @return admin
     */
    public Admin loadAdmin(String nick, String password);

    /**
     * Finds a player by its nick in the database
     * @param nick
     * @return player
     */
    public Player findPlayerByNick(String nick);

    /**
     * Updates the database with the current players data
     */
    public void updatePlayersDB();

    /**
     * Updates the database with the current administrators data
     */
    public void updateAdminsDB();

    /**
     * Updates the player ranking and returns it
     * @return ranking
     */
    public ArrayList<Player> updateRanking();
}
