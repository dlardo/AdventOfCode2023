import java.util.ArrayList;

public class Day3 {
  String day = "day3";
  AoC aoc = new AoC();
  ArrayList<String> data = aoc.getFile("data/" + day + "sample.txt");
  //ArrayList<String> data = aoc.getFile("data/" + day + ".txt");
  public static Character NULLCHAR = (char) 0;
  Day3Grid grid = new Day3Grid();
  int partNumberSum = 0;

  public Day3 () {
    System.out.println("Happy " + day + "!");
    buildGrid();

    // Find Valid Parts
    boolean validPart = false;
    ArrayList<Character> num = new ArrayList<Character>();
    for (int y = 0; y < grid.length(); y++) {
      for (int x = 0; x < grid.width(); x++) {
        char c = grid.getNodeValue(x, y);
        if (isDigit(c)) {
          num.add(c);
          if (adjacentToSpecial(x, y)) {
            validPart = true;
          }
        // number is finished / NaN
        } else if (num.size() > 0 && validPart) {
          partNumberSum += getPartNumber(num);
          num.clear();
          validPart = false;
        } else {
          num.clear();
          validPart = false;
        }
      }
    }
  }

  private int getPartNumber(ArrayList<Character> c) {
    String s = "";
    for (int i = 0; i < c.size(); i++) {
      s += c.get(i);
    }
    return Integer.parseInt(s);
  }

  private boolean adjacentToSpecial(int x, int y) {
    return (
    grid.isSpecial(grid.traverseDirection(x,y, CardinalDirection.N, 1).get(0)) ||
    grid.isSpecial(grid.traverseDirection(x,y, CardinalDirection.S, 1).get(0)) ||
    grid.isSpecial(grid.traverseDirection(x,y, CardinalDirection.E, 1).get(0)) ||
    grid.isSpecial(grid.traverseDirection(x,y, CardinalDirection.W, 1).get(0)) ||
    grid.isSpecial(grid.traverseDirection(x,y, CardinalDirection.NE, 1).get(0)) ||
    grid.isSpecial(grid.traverseDirection(x,y, CardinalDirection.NW, 1).get(0)) ||
    grid.isSpecial(grid.traverseDirection(x,y, CardinalDirection.SE, 1).get(0)) ||
    grid.isSpecial(grid.traverseDirection(x,y, CardinalDirection.SW, 1).get(0))
    );
  }

  private void buildGrid() {
    // Load data into grid
    for (int y = 0; y < data.size(); y++) {
      for (int x = 0; x < data.get(y).length(); x++) {
        grid.addNode(x, y, data.get(y).charAt(x));
      }
    }
  }

  private boolean isDigit(Character c) {
    if ((c > 47 && c < 58)) return true;
    return false;
  }

  public int getPart1Result() {
    return partNumberSum;
  }

  public int getPart2Result() {
    return -1;
  }
}
