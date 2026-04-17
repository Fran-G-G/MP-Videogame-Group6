package DB;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class DBManager {

    private HashMap<String, String> data = new HashMap<>();

    public DBManager(){
        try {
            loadData("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void serviceMethod(){
    }

    public boolean checkUser(String user, String password){
        return data.containsKey(user) && data.get(user).equals(password);
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
            String dataToWrite = data;
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
            String [] fields = data.split(",");
            fields[0] = fields[0].replace("%", "");
            fields[1] = fields[1].replace("%", "");

            String user = fields[0];
            String password = fields[1];
            this.data.put(user, password);

            data = buf.readLine();
        }
    }
}
