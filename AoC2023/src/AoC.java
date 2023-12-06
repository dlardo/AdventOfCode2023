import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AoC {

  public ArrayList<String> getFile(String filename) {
    return getFile(filename, Integer.MAX_VALUE);
  }

  public ArrayList<String> getFile(String filename, int limit) {
    ArrayList<String> result = new ArrayList<>();
    try {
      File inputFile = new File(filename);
      FileReader fR = new FileReader(inputFile);
      BufferedReader reader = new BufferedReader(fR);

      String line;
      while ((line = reader.readLine()) != null && limit > 0) {
        result.add(line);
        limit--;
      }
      reader.close();
    } catch (FileNotFoundException e) {
      System.out.println("Error: " + e.getMessage());
      System.exit(-1);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }
}
