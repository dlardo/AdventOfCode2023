import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Day3 {
  String day = "day3";
  AoC aoc = new AoC();
  //ArrayList<String> data = aoc.getFile("data/" + day + "sample.txt");
  ArrayList<String> data = aoc.getFile("data/" + day + ".txt");
  public static Character NULLCHAR = (char) 0;
  Day3Grid grid = new Day3Grid();
  int partNumberSum = 0;
  long gearRatioSum = 0;

  public Day3 () {
    System.out.println("Happy " + day + "!");
    buildGrid();
    calcValidParts();
    calcGearCoords();
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

  private void calcGearCoords() {

    // format: "Star Coords": [adjacent number, adjacent number, ...]
    //   e.g., 3,1: [467, 35]
    HashMap<String, Set<Integer>> stars = new HashMap<String, Set<Integer>>();

    // check every x,y, find numbers
    ArrayList<Character> num = new ArrayList<Character>();
    ArrayList<ArrayList<Integer>> starList = new ArrayList<ArrayList<Integer>>();
    for (int y = 0; y < grid.length(); y++) {
      for (int x = 0; x < grid.width(); x++) {
        char c = grid.getNodeValue(x, y);
        if (grid.isDigit(c)) {
          num.add(c);
          // if number is next to a star, get the star's coords
          for (ArrayList<Integer> coordSet : grid.getCoordsOfNeighboringStars(x,y)) {
            // store "star coords": [completed number]
            starList.add(coordSet);
          }
        } else if (num.size() > 0) {
          // store num
          for (ArrayList<Integer> xyPair : starList) {
            String key = Integer.toString(xyPair.get(0)) + "," + Integer.toString(xyPair.get(1));
            if (!stars.containsKey(key)) {
              stars.put(key, new HashSet<Integer>());
              stars.get(key).add(charArrayListToInteger(num));
            } else {
              stars.get(key).add(charArrayListToInteger(num));
            }
          }
          starList.clear();
          num.clear();
        }
      }
    }

    // if a "star coords" key has an array with exactly 2 numbers in it, it's a gear.
    for (String coord : stars.keySet()) {
      Set<Integer> set = stars.get(coord);
      if (set.size() == 2) {
        Iterator<Integer> it = set.iterator();
        int subTotal = (it.next() * it.next());
        System.out.println("Subtotal: " + subTotal);
        gearRatioSum += subTotal;
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
    return gearRatioSum;
  }
}
