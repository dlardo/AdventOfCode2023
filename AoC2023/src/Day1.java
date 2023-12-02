import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Day1 {
    // Part 2 answer: 55652
    AoC aoc = new AoC();
    ArrayList<String> data = aoc.getFile("data/day1.txt");
    long total = 0;

    ArrayList<String> engDigits = new ArrayList<>(
            Arrays.asList("zero", "one", "two", "three", "four",
                    "five", "six", "seven", "eight", "nine"));

    public Day1() {
        int subTotal;
        for (String line : data) {
            TreeMap<Integer, Integer> combinedDigits = new TreeMap<>();
            System.out.println("");

            for (Map.Entry<Integer, Integer> entry : getWordDigits(line).entrySet()) {
                combinedDigits.put(entry.getKey(), entry.getValue());
            }

            for (Map.Entry<Integer, Integer> entry : getDigitDigits(line).entrySet()) {
                combinedDigits.put(entry.getKey(), entry.getValue());
            }

            System.out.println("Found Digits: " + combinedDigits);
            System.out.println("Line: [" + line + "]");
            int first = combinedDigits.firstEntry().getValue();
            int last = combinedDigits.lastEntry().getValue();
            System.out.println("First: " + first +
                    " Last: " + last);
            subTotal = first * 10 + last;
            System.out.println("SubTotal: " + subTotal);
            total += subTotal;
        }
    }

    // returns HashMap<Location, Value>
    public HashMap<Integer, Integer> getWordDigits(String s) {
        HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
        // for each digit word
        for (int i = 1; i < engDigits.size(); i++) {
            int start = 0;
            // search through the string, noting every instance of the word
            while (start < s.length()) {
                int foundAt = s.indexOf(engDigits.get(i), start);
                if (foundAt >= 0) {
                    result.put(foundAt, i);
                    start = foundAt + 1;
                } else {
                    start = s.length();
                }
            }
        }
        return result;
    }

    public HashMap<Integer, Integer> getDigitDigits(String s) {
        HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
        // for each digit 0-9
        for (int i = 0; i < 10; i++) {
            int start = 0;
            // search through the string, noting every instance of the digit
            while (start < s.length()) {
                int foundAt = s.indexOf(Integer.toString(i), start);
                if (foundAt >= 0) {
                    result.put(foundAt, i);
                    start = foundAt + 1;
                } else {
                    start = s.length();
                }
            }
        }
        return result;
    }

    public long getResult() {
        return total;
    }
}
