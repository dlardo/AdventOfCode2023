import java.util.ArrayList;
import java.util.Arrays;

// grid is quadrant 1 x.y
//  2.0   2.1   2.2
//  1.0   1.1   1.2
//  0.0   0.1   0.2

public class Day3Grid {
  public static Character NULLCHAR = (char) 0;
  ArrayList<ArrayList<Character>> grid = new ArrayList<ArrayList<Character>>();

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

  // returns ArrayList of Chars adjacent to the starting point at x,y and direction indicated
  public ArrayList<Character> traverseDirection(int x, int y, CardinalDirection cd, int limit) {
    ArrayList<Character> result = new ArrayList<>();
    int[] c = getNeighborCoords(x, y, cd);
    while (getNodeValue(c[0], c[1]) != NULLCHAR && result.size() < limit) {
      result.add(getNodeValue(c[0], c[1]));
      c = getNeighborCoords(c[0], c[1], cd);
    }
    return result;
  }

  // returns list of Coordinates of special chars
  public ArrayList<ArrayList<Integer>> getSpecialCoords() {
    ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
    for (int y = 0; y < grid.size(); y++) {
      for (int x = 0; x < grid.get(y).size(); x++) {
        char c = grid.get(y).get(x);
        if (isSpecial(c)) {
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

  public int length() {
    return grid.size();
  }

  public int width() {
    if (length() == 0) return 0;
    return grid.get(0).size();
  }
}
