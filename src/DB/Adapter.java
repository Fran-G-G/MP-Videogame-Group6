package DB;

public class Adapter implements AdapterInterface{
    private DBManager adapter;

    @Override
    public void method() {

    }

    public void prueba(){
        adapter = new DBManager();
        adapter.writeToLog("Patatas, prueba");
        adapter.writeToLog("Segunda linea");
    }
}
