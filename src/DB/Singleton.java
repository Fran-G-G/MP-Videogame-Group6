package DB;

import Game.AbstractCharacter;
import Game.Player;

public class Singleton {
    private static final Singleton instance = new Singleton();
    private final Adapter adapter = new Adapter();

    private Singleton(){
    }

    public static Singleton getInstance(){
        return instance;
    }

    public void registerUser(String name, String nick, String password, String registrationNumber){
        adapter.registerUser(name, nick, password, registrationNumber);
    }

    public void registerCharacter(AbstractCharacter character) {
        adapter.registerCharacter(character);
    }

    public boolean checkUser(String user, String password){
        return adapter.checkUser(user, password);
    }

    public Player loadPlayer(String nick, String password){
        return adapter.loadPlayer(nick, password);
    }

    public Player findPlayerByNick(String nick){
        return adapter.findPlayerByNick(nick);
    }

}
