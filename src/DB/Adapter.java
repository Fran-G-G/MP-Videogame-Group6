package DB;

public class Adapter implements AdapterInterface{
    private DBManager adapter;

    @Override
    public void writeData() {
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
