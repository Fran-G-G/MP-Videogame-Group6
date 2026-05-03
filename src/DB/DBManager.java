package DB;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import Game.Admin;
import Game.Player;

public class DBManager {

    private String playersFile;
    private String adminsFile;
    private HashMap<String, Player> playersData = new HashMap<>();
    private HashMap<String, Admin> adminsData = new HashMap<>();

    public DBManager(){
        playersFile = "./config/players.dat";
        adminsFile = "./config/admins.dat";
        loadPlayers();
        loadAdmins();
    }

    public DBManager(String playersFile, String adminsFile){
        this.playersFile = playersFile;
        this.adminsFile = adminsFile;
        loadPlayers();
        loadAdmins();
    }

    /**
     * Registers a player in the database
     * @param player
     */
    public void registerPlayer(Player player){
        playersData.put(player.getNick(), player);
        updatePlayersDB();
    }

    /**
     * Deletes a player from the database
     * @param player
     */
    public void deletePlayer(Player player){
        if (player != null){
            playersData.remove(player.getNick());
        }
        updatePlayersDB();
    }

    /**
     * Registers an administrator in the database
     * @param admin
     */
    public void registerAdmin(Admin admin){
        adminsData.put(admin.getNick(), admin);
        updateAdminsDB();
    }

    /**
     * Deletes an administrator from the database
     * @param admin
     */
    public void deleteAdmin(Admin admin){
        if (admin != null){
            adminsData.remove(admin.getNick());
        }
        updateAdminsDB();
    }

    /**
     * Loads a player from the database
     * @param nick
     * @param password
     * @return player
     */
    public Player loadPlayer(String nick, String password) {
        if (playersData.containsKey(nick) && playersData.get(nick).getPassword().equals(password)){
            return playersData.get(nick);
        }else {
            return null;
        }
    }

    /**
     * Loads an administrator from the database
     * @param nick
     * @param password
     * @return admin
     */
    public Admin loadAdmin(String nick, String password){
        if (adminsData.containsKey(nick) && adminsData.get(nick).getPassword().equals(password)){
            return adminsData.get(nick);
        }else {
            return null;
        }
    }

    /**
     * Loads all players data
     */
    public void loadPlayers(){
        File file = new File(playersFile);
        if (!file.exists() || file.length() == 0) {
            playersData = new HashMap<>();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
            new FileInputStream(file))) {
            playersData = (HashMap<String, Player>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads all administrators data
     */
    public void loadAdmins(){
        File file = new File(adminsFile);
        if (!file.exists() || file.length() == 0) {
            adminsData = new HashMap<>();
            Admin admin = new Admin("Admin", "Admin1", "12345678");
            adminsData.put("Admin1", admin);
            updateAdminsDB();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file))) {
            adminsData = (HashMap<String, Admin>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds a player by its nick in the database
     * @param nick
     * @return player
     */
    public Player findPlayerByNick(String nick){
        if (playersData.containsKey(nick)){
            return playersData.get(nick);
        }else {
            return null;
        }
    }

    /**
     * Updates the database with the current players data
     */
    public void updatePlayersDB(){
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new FileOutputStream(playersFile)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.writeObject(playersData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the database with the current administrators data
     */
    public void updateAdminsDB(){
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new FileOutputStream(adminsFile)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.writeObject(adminsData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the player ranking and returns it
     * @return ranking
     */
    public ArrayList<Player> updateRanking(){
        ArrayList<Player> ranking = new ArrayList<>();

        for (Player player : playersData.values()){
            if (player.getCharacter() != null){
                ranking.add(player);
            }
        }

        ranking.sort(
                Comparator.comparing((Player p) -> p.getCharacter().getGold())
                        .reversed()
        );

        return ranking;
    }
}
