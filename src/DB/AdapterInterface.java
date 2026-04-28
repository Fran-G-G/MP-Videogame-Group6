package DB;

import Game.Player;

public interface AdapterInterface {
    public void registerUser(String user, String nick, String password, String registrationNumber);
    public boolean checkUser(String user, String password);
    public Player findPlayerByNick(String nick);
    public Player loadPlayer(String nick, String password);
}
