package DB;

import Game.Admin;
import Game.Player;

import java.util.ArrayList;

public class Adapter implements AdapterInterface{
    private DBManager DB;

    public Adapter(){
        this.DB = new DBManager();
    }

    @Override
    public void registerPlayer(Player player){
        DB.registerPlayer(player);
    }

    @Override
    public void deletePlayer(Player player){
        DB.deletePlayer(player);
    }

    @Override
    public void registerAdmin(Admin admin){
        DB.registerAdmin(admin);
    }

    @Override
    public void deleteAdmin(Admin admin){
        DB.deleteAdmin(admin);
    }

    @Override
    public boolean nickAvailable(String nick){
        return DB.nickAvailable(nick);
    }

    @Override
    public Player loadPlayer(String nick, String password){
        return DB.loadPlayer(nick, password);
    }

    @Override
    public Admin loadAdmin(String nick, String password) {
        return DB.loadAdmin(nick, password);
    }

    @Override
    public Player findPlayerByNick(String nick){
        return DB.findPlayerByNick(nick);
    }

    @Override
    public void updatePlayersDB(){
        DB.updatePlayersDB();
    }

    @Override
    public void updateAdminsDB(){
        DB.updateAdminsDB();
    }

    @Override
    public ArrayList<Player> updateRanking(){
        return DB.updateRanking();
    }
}
