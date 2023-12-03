import java.util.ArrayList;

public class Day3 {
  String day = "day3";
  AoC aoc = new AoC();
  ArrayList<String> data = aoc.getFile("data/" + day + "sample.txt");
  ArrayList<ArrayList<Day3Graph>> grid = new ArrayList<ArrayList<Day3Graph>>();
  int gridWidth = 0;
  int gridLength = 0;
  Day3Graph origin = new Day3Graph();

  public Day3 () {
    System.out.println("Happy " + day + "!");
    gridWidth = data.get(0).length();
    gridLength = data.size();

    // grid is quadrant 1 x.y
    //  2.0   2.1   2.2
    //  1.0   1.1   1.2
    //  0.0   0.1   0.2

    // data is quadrant 1 but it's upside down from the file input
    for (int y = 0; y < data.size(); y++) {
      grid.add(new ArrayList<Day3Graph>());

      for (int x = 0; x < gridWidth; x++) {
        char c = data.get(y).charAt(x);
        grid.get(y).add(new Day3Graph(x, y, c));
      }
    }

    origin = grid.get(0).get(0);

    // printFirstColumn();
    traverseFirstColumn();
  }

  private void printFirstColumn() {
    for (int y = 0; y < grid.size(); y++) {
      System.out.println(grid.get(y).get(0).val);
    }
  }

  private void traverseFirstColumn() {
    Day3Graph node = origin;

    while (true) {
      System.err.println(node.val);

      if (node.s.get("y") < gridLength) {
        int nextX = node.s.get("x");
        int nextY = node.s.get("y");
        node = grid.get(nextY).get(nextX);
      } else {
        break;
      }
    }
  }

  public int getPart1Result() {
    return -1;
  }

  public int getPart2Result() {
    return -1;
  }
}
