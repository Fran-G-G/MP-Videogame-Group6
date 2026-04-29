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

//    private HashMap<String, String[]> data = new HashMap<>();
    private HashMap<String, Player> playersData = new HashMap<>();
    private HashMap<String, Admin> adminsData = new HashMap<>();
//    private HashMap<String, AbstractCharacter> characterData  = new HashMap<>();

    public DBManager(){
        loadPlayers();
        loadAdmins();
//        try {
//            loadData("./config/" + "data" + ".txt");
//            loadPlayers();
//            loadAdmins();
//            loadCharacters();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

//    public void registerUser(String name, String nick, String password, String registrationNumber){
//        writeData(name + " " + nick + " " + password + " " + registrationNumber);
//    }

    public void registerPlayer(Player player){
        playersData.put(player.getNick(), player);
        updatePlayersDB();
    }

    public void registerAdmin(Admin admin){
        adminsData.put(admin.getNick(), admin);
        updateAdminsDB();
    }

//    public boolean checkUser(String user, String password){
//        return (playersData.containsKey(user) && playersData.get(user).getPassword().equals(password));
//    }

    public Player loadPlayer(String nick, String password) {
        if (playersData.containsKey(nick) && playersData.get(nick).getPassword().equals(password)){
            return playersData.get(nick);
        }else {
            return null;
        }
    }

    public Admin loadAdmin(String nick, String password){
        if (adminsData.containsKey(nick) && adminsData.get(nick).getPassword().equals(password)){
            return adminsData.get(nick);
        }else {
            return null;
        }
    }

    public void loadPlayers(){
//        Player player;
//
//        try (ObjectInputStream ois = new ObjectInputStream(
//                new FileInputStream("./config/players.dat"))) {
//            while (true) {
//                try {
//                    player = (Player) ois.readObject();
//                    playersData.put(player.getNick(), player);
//                } catch (EOFException e) {
//                    break;
//                }
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
        File file = new File("./config/players.dat");
        if (!file.exists() || file.length() == 0) {
            playersData = new HashMap<>();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
            new FileInputStream(file))) {
            playersData = (HashMap<String, Player>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAdmins(){
//        Admin admin;
//
//        try (ObjectInputStream ois = new ObjectInputStream(
//                new FileInputStream("./config/admins.dat"))) {
//            while (true) {
//                try {
//                    admin = (Admin) ois.readObject();
//                    adminsData.put(admin.getNick(), admin);
//                } catch (EOFException e) {
//                    break;
//                }
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
        File file = new File("./config/admins.dat");
        if (!file.exists() || file.length() == 0) {
            adminsData = new HashMap<>();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file))) {
            adminsData = (HashMap<String, Admin>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Player findPlayerByNick(String nick){
        if (playersData.containsKey(nick)){
            return playersData.get(nick);
        }else {
            return null;
        }
    }

    public void updatePlayersDB(){
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new FileOutputStream("./config/players.dat")
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.writeObject(playersData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAdminsDB(){
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new FileOutputStream("./config/admins.dat")
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.writeObject(adminsData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.close();
        } catch (IOException e) {
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

//    public void loadCharacters(){
//        AbstractCharacter character;
//
//        try (ObjectInputStream ois = new ObjectInputStream(
//                new FileInputStream("./config/characters.dat"))) {
//            while (true) {
//                try {
//                    character = (AbstractCharacter) ois.readObject();
//                    characterData.put("1", character);
//                } catch (EOFException e) {
//                    break;
//                }
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public void writeData(String data){
//        Path path = Paths.get("./config/" + "data" + ".txt");
//        if (!Files.exists(path)) {
//            try {
//                Files.createFile(path);
//            } catch (IOException ex) {
//                System.getLogger(DBManager.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
//            }
//        }
//
//        Writer out;
//        try {
//            out = new FileWriter("./config/" + "data" + ".txt", true);
//            BufferedWriter buf = new BufferedWriter(out);
//            buf.write(data);
//            buf.newLine();
//            buf.close();
//        } catch (IOException ex) {
//            System.getLogger(DBManager.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
//        }
//    }

//    public void loadData(String fileName) throws FileNotFoundException, IOException{
//        Reader in = new FileReader(fileName);
//        BufferedReader buf = new BufferedReader(in);
//        String data = buf.readLine();
//
//        while (data != null){
//            String [] fields = data.split(" ");
//            fields[0] = fields[0].replace("%", "");
//            fields[1] = fields[1].replace("%", "");
//            fields[2] = fields[2].replace("%", "");
//            fields[3] = fields[3].replace("%", "");
//
//            String name = fields[0];
//            String nick = fields[1];
//            String password = fields[2];
//            String registrationNumber = fields[3];
//            String[] userData = {name, password, registrationNumber};
//
//            this.data.put(nick, userData);
//
//            data = buf.readLine();
//        }
//    }
}
