class CodeDecode {

    public static String code(String s) {
        int sideLength = getSideLength(s);
        int desiredTextLength = sideLength * sideLength;

        for (int i = s.length(); i < desiredTextLength; i++) {
          s += "" + (char) 11;
        }

        s = rotateClockwise(s);

        String boxedString = "";
        for (int i = 0; i < s.length() - sideLength; i += sideLength) {
          boxedString += s.substring(i, i + sideLength) + "\n";
        }
        boxedString += s.substring(s.length() - sideLength);

        return boxedString;
    }
    public static String decode(String s) {
      s = s.replaceAll("\n", "");
      s = rotateCounterClockwise(s);
      s = s.replaceAll("" + (char)11, "");
      return s;
    }

    public static int getSideLength(String s) {
      return (int) Math.ceil(Math.sqrt(s.length()));
    }

    public static String rotateClockwise(String s) {
        s = mirrorOverXeqY(s);
        s = mirrorOverY(s);
        return s;
    }
    public static String rotateCounterClockwise(String s) {
        s = mirrorOverY(s);
        s = mirrorOverXeqY(s);
        return s;
    }
    public static String mirrorOverY(String s) {
      int sideLength = getSideLength(s);
      for (int i = 0; i < sideLength / 2; i++) {
        for (int j = 0; j < sideLength; j++) {
          int stringPos = sideLength * j + i;
          int otherPos = sideLength * j + (sideLength - i - 1);
          s = swapChars(s, stringPos, otherPos);
        }
      }
      return s;
    }
    public static String mirrorOverXeqY(String s) {
      int sideLength = getSideLength(s);
      for (int i = 0; i < sideLength; i++) {
        for (int j = 0; j < i; j++) {
          // \n not part of the rotation
          int stringPos = sideLength * j + i;
          int otherPos = sideLength * i + j;
          s = swapChars(s, stringPos, otherPos);
        }
      }
      return s;
    }
    public static String swapChars(String s, int pos1, int pos2) {
      char temp = s.charAt(pos1);
      s = s.substring(0, pos1) + s.charAt(pos2) + s.substring(pos1 + 1);
      s = s.substring(0, pos2) + temp + s.substring(pos2 + 1);
      return s;
    }
}