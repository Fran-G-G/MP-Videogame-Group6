package DB;

public class Adapter implements AdapterInterface{
    private DBManager adapter;

    public Adapter(){
        this.adapter = new DBManager();
    }

    @Override
    public void registerUser(String name, String nick, String password, String registrationNumber) {
        adapter.registerUser(name, nick, password, registrationNumber);
    }

    public void readData(){
    }

    public boolean checkUser(String user, String password){
        return adapter.checkUser(user, password);
    }

    public void prueba(){
        adapter = new DBManager();
        adapter.writeData("Menganito 12345678");
        adapter.writeData("Fulanito 12345678");
    }
}
