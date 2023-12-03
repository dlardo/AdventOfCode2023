import java.util.HashMap;

public class Day3Graph {
  int x = 0;
  int y = 0;
  char val = (char)0;
  HashMap<String,Integer> n = new HashMap<String,Integer>();
  HashMap<String,Integer> s = new HashMap<String,Integer>();
  HashMap<String,Integer> e = new HashMap<String,Integer>();
  HashMap<String,Integer> w = new HashMap<String,Integer>();
  HashMap<String,Integer> ne = new HashMap<String,Integer>();
  HashMap<String,Integer> nw = new HashMap<String,Integer>();
  HashMap<String,Integer> se = new HashMap<String,Integer>();
  HashMap<String,Integer> sw = new HashMap<String,Integer>();

  public Day3Graph() {}

  public Day3Graph(int x, int y, char val) {
    this.x = x;
    this.y = y;
    this.val = val;

    n.put("x", x);
    n.put("y", y - 1);
    s.put("x", x);
    s.put("y", y + 1);
    e.put("x", x + 1);
    e.put("y", y);
    w.put("x", x - 1);
    w.put("y", y);

    ne.put("x", x - 1);
    ne.put("y", y - 1);
    nw.put("x", x + 1);
    nw.put("y", y - 1);
    se.put("x", x + 1);
    se.put("y", y + 1);
    sw.put("x", x - 1);
    sw.put("y", y + 1);
  }
}
