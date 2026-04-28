package DB;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import Game.AbstractCharacter;
import Game.Admin;
import Game.Player;

public class DBManager {

    private HashMap<String, String[]> data = new HashMap<>();
    private HashMap<String, Player> playerData = new HashMap<>();
    private HashMap<String, Admin> adminData = new HashMap<>();
    private HashMap<String, AbstractCharacter> characterData  = new HashMap<>();

    public DBManager(){
        try {
            loadData("./config/" + "data" + ".txt");
            loadPlayers();
//            loadAdmins();
            loadCharacters();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerUser(String name, String nick, String password, String registrationNumber){
        writeData(name + " " + nick + " " + password + " " + registrationNumber);
    }

    public void registerPlayer(Player player){
        playerData.put(player.getNick(), player);

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new FileOutputStream("./config/players.dat")
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.writeObject(player);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerAdmin(Admin admin){
        adminData.put(admin.getNick(), admin);

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new FileOutputStream("./config/admins.dat")
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.writeObject(admin);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkUser(String user, String password){
        return data.containsKey(user) && data.get(user)[1].equals(password);
    }

    public Player loadPlayer(String nick, String password) {
        if (playerData.containsKey(nick) && playerData.get(nick).getPassword().equals(password)){
            return playerData.get(nick);
        }else {
            return null;
        }
    }

    public Admin loadAdmin(String nick, String password){
        if (adminData.containsKey(nick) && adminData.get(nick).getPassword().equals(password)){
            return adminData.get(nick);
        }else {
            return null;
        }
    }

    public void loadPlayers(){
        Player player;

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("./config/players.dat"))) {
            while (true) {
                try {
                    player = (Player) ois.readObject();
                    playerData.put(player.getNick(), player);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAdmins(){
        Admin admin;

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("./config/admins.dat"))) {
            while (true) {
                try {
                    admin = (Admin) ois.readObject();
                    adminData.put(admin.getNick(), admin);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerCharacter(AbstractCharacter character){
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new FileOutputStream("./config/characters.dat")
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.writeObject(character);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AbstractCharacter loadCharacter(){
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    new FileInputStream("./config/characters.dat")
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AbstractCharacter character = null;
        try {
            character = (AbstractCharacter) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            ois.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return character;
    }

    public void loadCharacters(){
        AbstractCharacter character;

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("./config/characters.dat"))) {
            while (true) {
                try {
                    character = (AbstractCharacter) ois.readObject();
                    characterData.put("1", character);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Player findPlayerByNick(String nick){
        if (playerData.containsKey(nick)){
            return playerData.get(nick);
        }else {
            return null;
        }
    }

    public void writeData(String data){
        Path path = Paths.get("./config/" + "data" + ".txt");
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException ex) {
                System.getLogger(DBManager.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }

        Writer out;
        try {
            out = new FileWriter("./config/" + "data" + ".txt", true);
            BufferedWriter buf = new BufferedWriter(out);
            buf.write(data);
            buf.newLine();
            buf.close();
        } catch (IOException ex) {
            System.getLogger(DBManager.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public void loadData(String fileName) throws FileNotFoundException, IOException{
        Reader in = new FileReader(fileName);
        BufferedReader buf = new BufferedReader(in);
        String data = buf.readLine();

        while (data != null){
            String [] fields = data.split(" ");
            fields[0] = fields[0].replace("%", "");
            fields[1] = fields[1].replace("%", "");
            fields[2] = fields[2].replace("%", "");
            fields[3] = fields[3].replace("%", "");

            String name = fields[0];
            String nick = fields[1];
            String password = fields[2];
            String registrationNumber = fields[3];
            String[] userData = {name, password, registrationNumber};

            this.data.put(nick, userData);

            data = buf.readLine();
        }
    }
}
