import java.util.ArrayList;

public class Day2 {
    AoC aoc = new AoC();
    ArrayList<String> data = aoc.getFile("data/day1sample.txt");

    public Day2() {
        for (int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i));
        }
    }

    public int getResult() {
        return -1;
    }
}
