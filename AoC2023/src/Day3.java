import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

// grid is quadrant 1 x.y
//  2.0   2.1   2.2
//  1.0   1.1   1.2
//  0.0   0.1   0.2

public class Day3 {
  String day = "day3";
  AoC aoc = new AoC();
  // ArrayList<String> data = aoc.getFile("data/" + day + "sample.txt");
  ArrayList<String> data = aoc.getFile("data/" + day + ".txt");
  public static Character NULLCHAR = (char) 0;
  ArrayList<ArrayList<Character>> grid = new ArrayList<ArrayList<Character>>();
  int partNumberSum = 0;
  long gearRatioSum = 0;

  public Day3() {
    System.out.println("Happy " + day + "!");
    buildGrid();
    calcValidParts();
    calcGearCoords();
  }

  private void calcValidParts() {
    boolean validPart = false;
    ArrayList<Character> num = new ArrayList<Character>();
    for (int y = 0; y < length(); y++) {
      for (int x = 0; x < width(); x++) {
        char c = getNodeValue(x, y);
        if (isDigit(c)) {
          num.add(c);
          if (adjacentToSpecial(x, y)) {
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
    // e.g., "3,1": [467, 35]
    HashMap<String, Set<Integer>> stars = new HashMap<String, Set<Integer>>();

    // check every x,y, find numbers
    ArrayList<Character> num = new ArrayList<Character>();
    ArrayList<ArrayList<Integer>> starList = new ArrayList<ArrayList<Integer>>();
    for (int y = 0; y < length(); y++) {
      for (int x = 0; x < width(); x++) {
        char c = getNodeValue(x, y);
        if (isDigit(c)) {
          num.add(c);
          // if number is next to a star, get the star's coords
          for (ArrayList<Integer> coordSet : getCoordsOfNeighboringStars(x, y)) {
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

    // if a "star coords" key has an array with exactly 2 numbers in it, it's a
    // gear.
    for (String coord : stars.keySet()) {
      Set<Integer> set = stars.get(coord);
      if (set.size() == 2) {
        Iterator<Integer> it = set.iterator();
        int subTotal = (it.next() * it.next());
        // System.out.println("Subtotal: " + subTotal);
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
        addNode(x, y, data.get(y).charAt(x));
      }
    }
  }

  public int getPart1Result() {
    return partNumberSum;
  }

  public long getPart2Result() {
    return gearRatioSum;
  }

  public char getNeighborValue(int x, int y, CardinalDirection cd) {
    int[] coords = getNeighborCoords(x, y, cd);
    return getNodeValue(coords[0], coords[1]);
  }

  public int[] getNeighborCoords(int x, int y, CardinalDirection cd) {
    switch (cd) {
      case N:
        return new int[] { x, y - 1 };
      case S:
        return new int[] { x, y + 1 };
      case E:
        return new int[] { x + 1, y };
      case W:
        return new int[] { x - 1, y };
      case NE:
        return new int[] { x - 1, y - 1 };
      case NW:
        return new int[] { x + 1, y - 1 };
      case SE:
        return new int[] { x + 1, y + 1 };
      case SW:
        return new int[] { x - 1, y + 1 };
      default:
        System.err.println("Invalid direction");
        return new int[] { 0 };
    }
  }

  public char getNodeValue(int x, int y) {
    if ((y > grid.size() - 1) || (y < 0))
      return NULLCHAR;
    if ((x > grid.get(y).size() - 1) || (x < 0))
      return NULLCHAR;
    return grid.get(y).get(x);
  }

  public void addNode(int x, int y, char val) {
    while (grid.size() < y + 1) {
      grid.add(new ArrayList<Character>());
    }

    // Fill in with empties if needed
    for (int i = grid.get(y).size(); i <= x; i++) {
      grid.get(y).add(NULLCHAR);
    }
    grid.get(y).set(x, val);
  }

  // starting at x,y, return all chars in that direction until the end of the grid
  public ArrayList<Character> traverseDirection(int x, int y, CardinalDirection cd) {
    return traverseDirection(x, y, cd, Integer.MAX_VALUE);
  }

  // returns ArrayList of Chars adjacent to the starting point at x,y and
  // direction indicated
  public ArrayList<Character> traverseDirection(int x, int y, CardinalDirection cd, int limit) {
    ArrayList<Character> result = new ArrayList<>();
    int[] c = getNeighborCoords(x, y, cd);
    while (getNodeValue(c[0], c[1]) != NULLCHAR && result.size() < limit) {
      result.add(getNodeValue(c[0], c[1]));
      c = getNeighborCoords(c[0], c[1], cd);
    }
    return result;
  }

  // returns list of Coordinates of stars chars
  public ArrayList<ArrayList<Integer>> getStarCoords() {
    ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
    for (int y = 0; y < grid.size(); y++) {
      for (int x = 0; x < grid.get(y).size(); x++) {
        char c = grid.get(y).get(x);
        if (isStar(c)) {
          result.add(new ArrayList<>(Arrays.asList(x, y)));
        }
      }
    }
    return result;
  }

  public boolean isSpecial(Character c) {
    if ((c > 32 && c < 46) ||
        (c == 47) ||
        (c > 57 && c < 65) ||
        (c > 90 && c < 97) ||
        (c > 123 && c < 127)) {
      return true;
    }
    return false;
  }

  public boolean isStar(Character c) {
    return (c == '*');
  }

  public boolean isDigit(Character c) {
    if ((c > 47 && c < 58))
      return true;
    return false;
  }

  public boolean adjacentToSpecial(int x, int y) {
    boolean result = false;
    for (CardinalDirection cd : CardinalDirection.values()) {
      if (traverseDirection(x, y, cd).size() > 0) {
        result = result || isSpecial(traverseDirection(x, y, cd, 1).get(0));
      }
    }
    return result;
  }

  public boolean adjacentToStar(int x, int y) {
    boolean result = false;
    for (CardinalDirection cd : CardinalDirection.values()) {
      if (traverseDirection(x, y, cd).size() > 0) {
        result = result || isStar(traverseDirection(x, y, cd, 1).get(0));
      }
    }
    return result;
  }

  public ArrayList<ArrayList<Integer>> getCoordsOfNeighboringStars(int x, int y) {
    ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
    for (CardinalDirection cd : CardinalDirection.values()) {
      ArrayList<Character> traverseResult = traverseDirection(x, y, cd, 1);
      if (traverseResult.size() > 0 && isStar(traverseResult.get(0))) {
        int[] neighxy = getNeighborCoords(x, y, cd);
        // adding new (x,y) pair
        result.add(new ArrayList<Integer>(Arrays.asList(neighxy[0], neighxy[1])));
      }
    }
    if (result.size() > 1) {
      System.out.println(x + ", " + y + " was next to multiple stars");
    }
    return result;
  }

  public ArrayList<Character> adjacentChars(int x, int y) {
    ArrayList<Character> result = new ArrayList<>();
    for (CardinalDirection cd : CardinalDirection.values()) {
      if (traverseDirection(x, y, cd).size() > 0) {
        char c = traverseDirection(x, y, cd, 1).get(0);
        if (c != NULLCHAR)
          result.add(c);
      }
    }
    return result;
  }

  public int length() {
    return grid.size();
  }

  public int width() {
    if (length() == 0)
      return 0;
    return grid.get(0).size();
  }
}
