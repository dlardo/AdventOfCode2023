import java.util.ArrayList;
import java.util.HashMap;

public class Day2 {
    AoC aoc = new AoC();
    ArrayList<String> data = aoc.getFile("AoC2023/data/day2.txt");
    int total = 0;
    public Day2() {
        for (int i = 0; i < data.size(); i++) {
            int game_id = i + 1;
            String line = data.get(i);
            HashMap<String, Integer> game = getHighestPulls(line);
            System.out.println("Highest Pulls for game " + game_id + ": " + game);
            if (isValidGame(game)) total += game_id;
        }
    }

    private Boolean isValidGame(HashMap<String,Integer> game) {
        if (!game.containsKey("red")) game.put("red", 0);
        if (!game.containsKey("blue")) game.put("blue", 0);
        if (!game.containsKey("green")) game.put("green", 0);

        if(game.get("red") > 12) return false;
        if(game.get("blue") > 14) return false;
        if(game.get("green") > 13) return false;

        return true;
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
        return total;
    }
}
