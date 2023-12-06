import java.util.ArrayList;

public class DayN {
  String day = "N";
  AoC aoc = new AoC();
  ArrayList<String> data = aoc.getFile("data/day" + day + "sample.txt");
  //ArrayList<String> data = aoc.getFile("data/day" + day + ".txt");

  public DayN() {
    System.out.println("Happy Day " + day + "!");
    System.out.println("https://adventofcode.com/2023/day/" + day);

    for (String line : data) {
      System.out.println(line);
    }
  }
  public int getPart1Result() {
    return -1;
  }

    public int getPart2Result() {
      return -1;
  }
}
