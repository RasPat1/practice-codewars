public class LCS {
    public static String lcs(String x, String y) {
      // dynanmic programming problem!
      // start at the end
      // the lcs is the longest lcs fo the substring + 1 if these guys match..

      // do we shorten x or y?
      // if they match we shorten x and y
      // if they dont we shorten x
      if (x.equals("") || y.equals("")) {
        return "";
      }
      if (x.equals(y)) {
        return x;
      }

      String maxString = "";
      String maxSubstring = "";

      if (x.charAt(x.length() - 1) == y.charAt(y.length() - 1)) {
        maxSubstring = lcs(x.substring(0, x.length() - 1), y.substring(0, y.length() - 1)) + x.charAt(x.length() - 1);
        maxString = maxSubstring.length() > maxString.length() ? maxSubstring : maxString;
      }
      if (x.length() > 1) {
        maxSubstring = lcs(x.substring(0, x.length() - 1), y);
        maxString = maxSubstring.length() > maxString.length() ? maxSubstring : maxString;
      }
      if (y.length() > 1) {
        maxSubstring = lcs(x, y.substring(0, y.length() - 1));
        maxString = maxSubstring.length() > maxString.length() ? maxSubstring : maxString;
      }

      return maxString;
    }
}