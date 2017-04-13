import java.util.*;
import java.util.regex.*;

public class Runes {

    public static int solveExpression( final String expression ) {
      int missingDigit = -1;

      for (int i = 0; i <= 9; i++) {
        String qCandidate = String.valueOf(i);
        if (expression.indexOf(qCandidate) == -1) {
          String newExpr = expression.replaceAll("\\?", qCandidate);
          if (isValidExpr(newExpr)) {
            return i;
          }
        }
      }

      return missingDigit;
    }

    public static Boolean isValidExpr(String s) {
      Pattern p = Pattern.compile("(-?\\d+)([\\+\\-\\*])(-?\\d+)=(-?\\d+)");
      Matcher m = p.matcher(s);
      List<String> parts = new ArrayList<>();

      while (m.find()) {
        for (int i = 0; i < m.groupCount(); i++) {
          String group = m.group(i+1);

          if (group.length() > 1 && group.charAt(0) == '0') {
            return false;
          }

          if (group.length() > 1 && group.charAt(0) == '-' && group.charAt(1) == '0') {
            return false;
          }

          parts.add(group);
        }
      }

      int n1 = Integer.valueOf(parts.get(0));
      char op = parts.get(1).charAt(0);
      int n2 = Integer.valueOf(parts.get(2));
      int n3 = Integer.valueOf(parts.get(3));

      switch (op) {
        case '+': return n1 + n2 == n3;
        case '-': return n1 - n2 == n3;
        case '*': return n1 * n2 == n3;
        default: break;
      }

      return false;
    }

}
