import DB.Singleton;

/**
 * Entry point of the application.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("  🧛‍♂️ VAMPIRE GAME - START 🐺");
        System.out.println("=================================\n");

//      Prueba del singleton, ignorar
//        Singleton singleton = new Singleton();
//        singleton.prueba();
        GameStarter starter = new GameStarter();
        starter.run();
    }
}