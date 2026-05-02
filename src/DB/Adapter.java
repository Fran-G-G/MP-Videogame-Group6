package DB;

import Game.Admin;
import Game.Player;

import java.util.ArrayList;

public class Adapter implements AdapterInterface{
    private DBManager adapter;

    public Adapter(){
        this.adapter = new DBManager();
    }

    @Override
    public void registerPlayer(Player player){
        adapter.registerPlayer(player);
    }

    @Override
    public void deletePlayer(Player player){
        adapter.deletePlayer(player);
    }

    @Override
    public void registerAdmin(Admin admin){
        adapter.registerAdmin(admin);
    }

    @Override
    public void deleteAdmin(Admin admin){
        adapter.deleteAdmin(admin);
    }

    @Override
    public Player loadPlayer(String nick, String password){
        return adapter.loadPlayer(nick, password);
    }

    @Override
    public Admin loadAdmin(String nick, String password) {
        return adapter.loadAdmin(nick, password);
    }

    @Override
    public Player findPlayerByNick(String nick){
        return adapter.findPlayerByNick(nick);
    }

    @Override
    public void updatePlayersDB(){
        adapter.updatePlayersDB();
    }

    @Override
    public void updateAdminsDB(){
        adapter.updateAdminsDB();
    }

    @Override
    public ArrayList<Player> updateRanking(){
        return adapter.updateRanking();
    }
}
