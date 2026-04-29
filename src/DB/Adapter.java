package DB;

import Game.AbstractCharacter;
import Game.Player;

public class Adapter implements AdapterInterface{
    private DBManager adapter;

    public Adapter(){
        this.adapter = new DBManager();
    }

    @Override
    public void registerUser(String name, String nick, String password, String registrationNumber) {
//        adapter.registerUser(name, nick, password, registrationNumber);
    }

    public void registerPlayer(Player player){
        adapter.registerPlayer(player);
    }

    public void registerCharacter(AbstractCharacter character) {
        adapter.registerCharacter(character);
    }

//    public boolean checkUser(String user, String password){
//        return adapter.checkUser(user, password);
//    }

    public Player loadPlayer(String nick, String password){
        return adapter.loadPlayer(nick, password);
    }

    public Player findPlayerByNick(String nick){
        return adapter.findPlayerByNick(nick);
    }

    public void updatePlayersDB(){
        adapter.updatePlayersDB();
    }
}
