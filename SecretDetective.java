import java.util.*;

public class SecretDetective {

  public String recoverSecret(char[][] triplets) {
    // topographic sort with unique solution
    // this means that there is one ordering
		// for each letter create a node
    // for each ordering create 2 edges

    // create a map to hold the nodes
    // have a charNodeClass
    // have a next array of other nodes
    // Do a graph search for longest path

    Map<Character, CharNode> map = new HashMap<Character, CharNode>();

    // load up triplets
    for (int i = 0; i < triplets.length; i++) {
      char[] triplet = triplets[i];
      for (int j = 0; j < triplet.length - 1; j++) {
        // make/retrieve charNodes and add edges
        CharNode cn1 = getNode(map, triplet[j]);
        CharNode cn2 = getNode(map, triplet[j + 1]);;
        cn1.addNext(cn2);
      }
    }

    // do graph search for longest path
    List<Character> fullPath = new ArrayList<Character>();
    // with memoization
    Map<Character, List<Character>> memo = new HashMap<Character, List<Character>>();
    for (Map.Entry<Character, CharNode> entry : map.entrySet()) {
      if (entry.getValue().incomingEdgeCount == 0) {
        fullPath = getLongestPath(entry.getValue(), memo);
      }
    }

    // convert list back to a string
    StringBuilder result = new StringBuilder();
    for (Character c : fullPath) {
      result.append("" + c);
    }

    return result.toString();
  }

  public static List<Character> getLongestPath(CharNode cn, Map<Character, List<Character>> memo) {
    if (memo.containsKey(cn.letter)) {
      return memo.get(cn.letter);
    } else {
      List<Character> result = new ArrayList<Character>();
      for (int i = 0; i < cn.next.size(); i++) {
        CharNode nextNode = cn.next.get(i);
        List<Character> specificPath = getLongestPath(nextNode, memo);
        result = specificPath.size() > result.size() ? specificPath : result;
      }

      // basecase
      result.add(0, cn.letter);
      memo.put(cn.letter, result);
      return result;
    }
  }

  public CharNode getNode(Map<Character, CharNode> map, char c) {
    if (map.containsKey(c)) {
      return map.get(c);
    } else {
      CharNode cn = new CharNode(c);
      map.put(c, cn);
      return cn;
    }
  }

  class CharNode {
    List<CharNode> next = new ArrayList<CharNode>();
    char letter;
    int incomingEdgeCount = 0;

    public CharNode(char letter) {
      this.letter = letter;
    }

    public void addNext(CharNode c2) {
      next.add(c2);
      c2.incomingEdgeCount++;
    }
  }
}