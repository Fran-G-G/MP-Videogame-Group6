package DB;

public interface AdapterInterface {
    public void registerUser(String user, String password);
    public void readData();
    public boolean checkUser(String user, String password);
}
