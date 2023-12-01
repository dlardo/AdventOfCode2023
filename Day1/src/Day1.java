import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Day1 {
    // 54983 too low
    // 55002 too low
    // 55739 too high
    ArrayList<String> data;
    long total = 0;

    ArrayList<String> engDigits = new ArrayList<>(
        Arrays.asList("zero", "one", "two", "three", "four", 
                      "five", "six", "seven", "eight", "nine"));

    public Day1() {
        data = getFile("data/day1sample.txt");
        int subTotal;
        for (String line : data) {
            System.out.println("");
            System.out.println("Line: [" + line + "]");
            System.out.println("First: " + getFirst(line) + 
                               " Last: " + getLast(line));
            subTotal = getFirst(line) * 10 + getLast(line);
            System.out.println("SubTotal: " + subTotal);
            total += subTotal;
            
        }
    }

    public int getFirst(String s) {
        int a = getFirstDigit(s);
        int b = getFirstWordWithDigit(s);
        return Math.min(a,b);
    }

    public int getLast(String s) {
        return Math.min(getLastWordWithDigit(s), getLastDigit(s));
    }

    public int getFirstWordWithDigit(String s) {
        for (int i = 3; i < s.length(); i++) {
            String g = s.substring(0, i + 1);
            for (int j = 1; j < engDigits.size(); j++) {
                if (g.contains(engDigits.get(j))) {
                    return j;
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    public int getLastWordWithDigit(String s) {
        for (int i = s.length() - 3; i >= 0; i--) {
            String g = s.substring(0, i + 1);
            for (int j = 1; j < engDigits.size(); j++) {
                if (g.contains(engDigits.get(j))) {
                    return j;
                }
            }
        }
        return Integer.MAX_VALUE;
    }
    
    public int getFirstDigit(String s) {
        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i);
            if (a > 47 && a < 58) {   // 0 = char 48, 9 = char 57
                // System.out.println("First: " + a + ":" + (short)a);
                return (int) (a - 48);
            }
        }
        return Integer.MAX_VALUE;
    }

    public int getLastDigit(String s) {
        for (int i = s.length() - 1; i >= 0; i--) {
            char a = s.charAt(i);
            if (a > 47 && a < 58) {   // 0 = char 48, 9 = char 57
                // System.out.println("Last:" + a + ":" + (short)a);
                return (int) (a - 48);
            }
        }
        return Integer.MAX_VALUE;
    }

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
                // System.out.println(line);
                result.add(line);
                limit--;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public long getResult() {
        return total;
    }
}
