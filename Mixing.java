import java.util.*;

public class Mixing {

    public static String mix(String s1, String s2) {
      // take in two strings
      // find the counts of each lowercase letter
      // get teh maximum count of that letter and what string it came from
      // construct a string consisting of the maxiumums sorted by max then sorted by lex order

      Map<Character, Integer> map1 = getCounts(s1);
      Map<Character, Integer> map2 = getCounts(s2);
      List<MixHelper> mixes = new ArrayList<>();

      fillHelperFromMap(map1, map2, mixes, '1');
      fillHelperFromMap(map2, map1, mixes, '2');

      Collections.sort(mixes, new Comparator<MixHelper>() {
        public int compare(MixHelper mh1, MixHelper mh2) {
          if (mh1.count == mh2.count) {
            if (mh1.sourceString == mh2.sourceString) {
              return mh1.c - mh2.c;
            } else {
              return mh1.sourceString - mh2.sourceString;
            }
          } else {
            return mh2.count - mh1.count;
          }
        }
      });

      List<String> mixStrings = new ArrayList<String>();
      for (MixHelper mh: mixes) {
        mixStrings.add(mh.toString());
      }

      return String.join("/", mixStrings);
    }

    public static void fillHelperFromMap(Map<Character, Integer> map1, Map<Character, Integer> map2, List<MixHelper> mixes, char defaultSource) {
      for (Iterator<Map.Entry<Character, Integer>> i1 = map1.entrySet().iterator(); i1.hasNext();) {
        Map.Entry<Character, Integer> e1 = i1.next();
        int count = e1.getValue();
        char c = e1.getKey();
        char sourceString = defaultSource;

        if (count > 1) { // if not ignore everything

          if (map2.containsKey(c)) {
            if (map2.get(c) == count) {
              sourceString = '=';
            } else if (map2.get(c) > count) {
              sourceString = defaultSource == '1' ? '2' : '1';
              count = map2.get(c);
            }
          }

          mixes.add(new MixHelper(sourceString, c, count));
          i1.remove();
          map2.remove(c);
        }

      }
    }

    public static Map<Character, Integer> getCounts(String s) {
      Map<Character, Integer> map = new HashMap<>();
      for (char c : s.toCharArray()) {
        if (c >= 'a' && c <= 'z') {
          if (!map.containsKey(c)) {
            map.put(c, 0);
          }
          map.put(c, map.get(c) + 1);
        }
      }

      return map;
    }
}

class MixHelper {
  Character sourceString;
  Character c;
  Integer count;

  public MixHelper(Character sourceString, Character c, Integer count) {
    this.sourceString = sourceString;
    this.c = c;
    this.count = count;
  }

  @Override
  public String toString() {
    String chars = "";
    for (int i = 0; i < count; i++) {
      chars += c + "";
    }
    return sourceString + ":" + chars;
  }
}