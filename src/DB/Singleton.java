package DB;

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

    public boolean checkUser(String user, String password){
        return adapter.checkUser(user, password);
    }

    public void prueba(){
        adapter.prueba();
    }
}
