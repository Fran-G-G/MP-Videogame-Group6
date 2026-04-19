package DB;

public class Singleton {
    private static final Singleton instance = new Singleton();
    private final Adapter adapter = new Adapter();

    private Singleton(){
    }

    public static Singleton getInstance(){
        return instance;
    }

    public void registerUser(String user, String password){
        adapter.registerUser(user, password);
    }

    public boolean checkUser(String user, String password){
        return adapter.checkUser(user, password);
    }

    public void prueba(){
        adapter.prueba();
    }
}
