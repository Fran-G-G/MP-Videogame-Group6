package DB;

public class Singleton {
    private static final Singleton instance = new Singleton();
    private final Adapter adapter = new Adapter();

    private Singleton(){
    }

    public static Singleton getInstance(){
        return instance;
    }

    public void prueba(){
        adapter.prueba();
    }
}
