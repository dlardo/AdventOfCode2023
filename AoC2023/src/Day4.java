import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {
  String day = "4";
  AoC aoc = new AoC();
  //ArrayList<String> data = aoc.getFile("data/day" + day + "sample.txt");
  ArrayList<String> data = aoc.getFile("data/day" + day + ".txt");
  double p1Points = 0;
  long p2Total = 0;
  HashMap<Integer, Double> scores = new HashMap<Integer, Double>();
  HashMap<Integer, Integer> matches = new HashMap<Integer, Integer>();
  HashMap<Integer, Integer> ticketCounts = new HashMap<Integer, Integer>();

  public Day4() {
    System.out.println("Happy Day " + day + "!");
    System.out.println("https://adventofcode.com/2023/day/" + day);

    scores = calcScoresForAllCards(data);
    // calc total
    for (Entry<Integer, Double> score : scores.entrySet()) {
      p1Points += score.getValue();
      ticketCounts.put(score.getKey(), 1);
    }

    // Part 2: Ticket Growth
    // ticketCounts is how many copies of the ticket there are
    // matches is a memoization table of the number of matches each card has. It should NOT be mutated.

    // for each card ID
    for (Entry<Integer, Integer> match : matches.entrySet()) {
      // Note: Enabling the debug prints will signifigantly slow down the execution.
      // System.out.println("Score Card: " + match.getKey() + " has " + match.getValue() + " matches in it. Waterfalling to the next " + match.getValue() + " cards.");

      // for each copy of the card
      for (int i = 1; i <= ticketCounts.get(match.getKey()); i++) {
        // System.out.println("Score Card: " + match.getKey() + " pass " + (i) + "/" + ticketCounts.get(match.getKey()) + ", Adding " + match.getValue() + " downstream copies");

        // add N copies to the downstream cards
        for (int j = match.getKey() + 1; j <= match.getKey() + match.getValue(); j++) {
          // j = card index at the top of the waterfall
          // System.out.println("Adding card to " + j + " ");
          ticketCounts.put(j, ticketCounts.get(j) + 1);
        }
      }
    }

    // Sum tickets
    for (int i = 1; i <= ticketCounts.size(); i++) {
      // System.out.println(i + ": " + ticketCounts.get(i));
      p2Total += ticketCounts.get(i);
    }
  }

  public HashMap<Integer, Double> calcScoresForAllCards(ArrayList<String> data) {
    HashMap<Integer, Double> result = new HashMap<Integer, Double>();
    int card = 0;
    for (String line : data) {
      card = getCardNumber(line);
      // System.out.print("Card " + getCardNumber(line) + ": ");
      Set<Integer> winners = getWinners(line);
      Set<Integer> yourNums = getYourNumbers(line);
      // System.out.print("Winners: " + winners + " ");
      // System.out.print("Your Numbers: " + yourNums + " ");

      Set<Integer> intersection = new TreeSet<>(winners);
      intersection.retainAll(yourNums);
      // System.out.print("Matches: " + intersection + " ");

      // Store Matches for Part2
      matches.put(card, intersection.size());

      // Score
      double subtotal = 0;
      if (intersection.size() > 0) {
        subtotal = Math.pow((double)2, intersection.size() - 1);
      } else {
        subtotal = 0;
      }

      result.put(card, subtotal);
      // System.out.println("Score: " + subtotal);
    }
    return result;
  }

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

  public long getPart2Result() {
    return p2Total;
  }
}
