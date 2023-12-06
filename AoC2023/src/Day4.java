import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {
  String day = "4";
  AoC aoc = new AoC();
  //ArrayList<String> data = aoc.getFile("data/day" + day + "sample.txt");
  ArrayList<String> data = aoc.getFile("data/day" + day + ".txt");
  double p1Points = 0;

  public Day4() {
    System.out.println("Happy Day " + day + "!");
    System.out.println("https://adventofcode.com/2023/day/" + day);

    for (String line : data) {

      System.out.print("Card " + getCardNumber(line) + ": ");
      Set<Integer> winners = getWinners(line);
      Set<Integer> yourNums = getYourNumbers(line);
      System.out.print("Winners: " + winners + " ");
      System.out.print("Your Numbers: " + yourNums + " ");

      Set<Integer> intersection = new TreeSet<>(winners);
      intersection.retainAll(yourNums);
      System.out.print("Matches: " + intersection + " ");

      // score
      double subtotal = 0;
      if (intersection.size() > 0) {
        subtotal = Math.pow((double)2, intersection.size() - 1);
      } else {
        subtotal = 0;
      }

      System.out.println("Score: " + subtotal);
      p1Points += subtotal;
    }
  }

  // public int getCardNumber(String data) {
  public int getCardNumber(String data) {
    String regex = " (\\d+): ";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(data);
    m.find();
    return Integer.parseInt(m.group(1));
  }

  public Set<Integer> getWinners(String data) {
    Set<Integer> result = new TreeSet<Integer>();

    String[] matches = data.split(":")[1].split(" \\| ")[0].trim().split(" +");
    for (String m: matches) {
        result.add(Integer.parseInt(m));
    }
    return result;
  }

  public Set<Integer> getYourNumbers(String data) {
    Set<Integer> result = new TreeSet<Integer>();

    String[] matches = data.split(" \\| ")[1].trim().split(" +");
    for (String m: matches) {
      result.add(Integer.parseInt(m));
    }
    return result;
  }

  public double getPart1Result() {
    return p1Points;
  }

    public int getPart2Result() {
      return -1;
  }
}
