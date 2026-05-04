import Game.*;

public class Main {
    public static void main(String[] args) {

        boolean playing = true;

        while (playing) {
            System.out.println("=================================");
            System.out.println("  🧛‍♂️ PELEAS EN ULLEWAND 🐺");
            System.out.println("=================================\n");

            GameStarter gameStarter = new GameStarter();
            gameStarter.run();

            playing = !ConsoleInput.readBoolean("¿Desea cerrar la aplicación?");
        }
    }
}