import java.util.ArrayList;
import java.util.HashMap;

public class Day2 {
    AoC aoc = new AoC();
    ArrayList<String> data = aoc.getFile("data/day2sample.txt");

    public Day2() {
        for (int i = 0; i < data.size(); i++) {
            String line = data.get(i);
            HashMap<String, Integer> game = getHighestPulls(line);
            System.out.println("Highest Pulls for game " + i + ": " + game);
        }
    }

    private HashMap<String, Integer> getHighestPulls(String s) {
        HashMap<String, Integer> result = new HashMap<>();
        s = s.substring(s.indexOf(":") + 2, s.length());

        String[] splits = s.split("; ");
        for (int i = 0; i < splits.length; i++) {
            String[] pulls = splits[i].split(", ");
            for (int j = 0; j < pulls.length; j++) {
                String[] pull = pulls[j].split(" ");
                int value = Integer.parseInt(pull[0]);
                String color = pull[1];
                if (result.containsKey(color)) {
                    result.put(color, Math.max(result.get(color), value));
                } else {
                    result.put(color, value);
                }
            }
        }
        return result;
    }

    public int getResult() {
        return -1;
    }
}
