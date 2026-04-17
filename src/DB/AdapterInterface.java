package DB;

public interface AdapterInterface {
    public void writeData();
    public void readData();
    public boolean checkUser(String user, String password);
}
