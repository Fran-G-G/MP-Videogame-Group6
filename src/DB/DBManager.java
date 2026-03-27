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

public class DBManager {

    public void serviceMethod(){

    }

    public void writeToLog(String data){
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

    public void readLog(String fileName) throws FileNotFoundException, IOException{
        Reader in = new FileReader(fileName);
        BufferedReader buf = new BufferedReader(in);
        String data = buf.readLine();

        while (data != null){
            String [] fields = data.split(",");
            fields[0] = fields[0].replace("%", "");
                    fields[1] = fields[1].replace("%", "");

                            String text = fields[0];
            String translation = fields[1];
//            dictionary.put(text, translation);

            data = buf.readLine();
        }
    }
}
