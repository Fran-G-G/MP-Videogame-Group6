package DB;

public interface AdapterInterface {
    public void registerUser(String user, String nick, String password, String registrationNumber);
    public void readData();
    public boolean checkUser(String user, String password);
}
