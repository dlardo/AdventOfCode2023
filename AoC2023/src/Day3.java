import java.util.ArrayList;
import java.util.HashMap;

public class Day3 {
  String day = "day3";
  AoC aoc = new AoC();
  ArrayList<String> data = aoc.getFile("data/" + day + "sample.txt");
  //ArrayList<String> data = aoc.getFile("data/" + day + ".txt");
  public static Character NULLCHAR = (char) 0;
  Day3Grid grid = new Day3Grid();
  int partNumberSum = 0;
  long gearRatioSum = 0;

  public Day3 () {
    System.out.println("Happy " + day + "!");
    buildGrid();
    calcValidParts();
    calcValidGears();

  }

  private void calcValidParts() {
    boolean validPart = false;
    ArrayList<Character> num = new ArrayList<Character>();
    for (int y = 0; y < grid.length(); y++) {
      for (int x = 0; x < grid.width(); x++) {
        char c = grid.getNodeValue(x, y);
        if (grid.isDigit(c)) {
          num.add(c);
          if (grid.adjacentToSpecial(x, y)) {
            validPart = true;
          }
        // number is finished / NaN
        } else if (num.size() > 0 && validPart) {
          partNumberSum += charArrayListToInteger(num);
          num.clear();
          validPart = false;
        } else {
          num.clear();
          validPart = false;
        }
      }
    }
  }

  private void calcValidGears() {

    // Hashmap format: "Star Coords": [adjacent number, adjacent number, ...]
    //           e.g., "3,1" : [467, 35]
    HashMap<String, ArrayList<Integer>> stars = new HashMap<String, ArrayList<Integer>>();

    // if number is next to a star, get the star's coords
    // store "star coords": [completed number]
    // if a "star coords" key has an array with 2 numbers in it, it's a gear.

    ArrayList<Character> num = new ArrayList<Character>();
    ArrayList<ArrayList<Integer>> starList = new ArrayList<ArrayList<Integer>>();

    // check every x,y, find numbers
    for (int y = 0; y < grid.length(); y++) {
      for (int x = 0; x < grid.width(); x++) {
        char c = grid.getNodeValue(x, y);
        if (grid.isDigit(c)) {
          num.add(c);
          for (ArrayList<Integer> coordSet : grid.getCoordsOfNeighboringStars(x,y)) {
            starList.add(coordSet);
          }
        } else if (num.size() > 0) {
          // store num
          for (ArrayList<Integer> xyPair : starList) {
            String key = Integer.toString(xyPair.get(0)) + "," + Integer.toString(xyPair.get(1));
            if (!stars.containsKey(key)) {
              stars.put(key, new ArrayList<Integer>(charArrayListToInteger(num)));
            } else {
              stars.get(key).add(charArrayListToInteger(num));
            }
          }
          num.clear();
        }
      }
    }

    for (String starCoord : stars.keySet()) {
      System.out.println("Star at: " + starCoord);
      if (stars.get(starCoord).size() > 0) {
        System.out.println("Neighbors: " + stars.get(starCoord));
      }
    }
  }

  private int charArrayListToInteger(ArrayList<Character> c) {
    String s = "";
    for (int i = 0; i < c.size(); i++) {
      s += c.get(i);
    }
    return Integer.parseInt(s);
  }

  private void buildGrid() {
    // Load data into grid
    for (int y = 0; y < data.size(); y++) {
      for (int x = 0; x < data.get(y).length(); x++) {
        grid.addNode(x, y, data.get(y).charAt(x));
      }
    }
  }

  public int getPart1Result() {
    return partNumberSum;
  }

  public long getPart2Result() {
    return -1;
  }
}
