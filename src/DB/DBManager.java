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

    public void registerPlayer(Player player){
        playersData.put(player.getNick(), player);
        updatePlayersDB();
    }

    public void deletePlayer(Player player){
        playersData.remove(player.getNick());
        updatePlayersDB();
    }

    public void registerAdmin(Admin admin){
        adminsData.put(admin.getNick(), admin);
        updateAdminsDB();
    }

    public Player loadPlayer(String nick, String password) {
        if (playersData.containsKey(nick) && playersData.get(nick).getPassword().equals(password)){
            return playersData.get(nick);
        }else {
            return null;
        }
    }

    public Admin loadAdmin(String nick, String password){
        if (adminsData.containsKey(nick) && adminsData.get(nick).getPassword().equals(password)){
            return adminsData.get(nick);
        }else {
            return null;
        }
    }

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

    public Player findPlayerByNick(String nick){
        if (playersData.containsKey(nick)){
            return playersData.get(nick);
        }else {
            return null;
        }
    }

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

    public ArrayList<Player> updateRanking(){
        ArrayList<Player> ranking = new ArrayList<>();

        for (Player player : playersData.values()){
            if (player.getCharacter() != null){
                ranking.add(player);
            }
        }

        ranking.sort(Comparator.comparing(p -> p.getCharacter().getGold()));

        return ranking;
    }
}
