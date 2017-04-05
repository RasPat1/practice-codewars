import java.util.*;

public class Bowling {
  public static int bowling_score(String frames) {
    String[] framesArr = frames.split(" ");
    List<Character> balls = new ArrayList<>();
    List<Integer> ballValue = new ArrayList<>();
    int ballsBeforeLastFrame = frames.lastIndexOf(" ") - 9;

    for (char c : frames.toCharArray()) {
      if (c != ' ') {
        balls.add(c);
      }
    }

    int lastBallValue = 0;
    for (char c: balls) {
      int currentBallValue;
      if (c == '/') {
        currentBallValue = 10 - lastBallValue;

      } else {
        currentBallValue = getBallScore(c);
      }
      ballValue.add(currentBallValue);
      lastBallValue = currentBallValue;
    }

    int sum = 0;
    for (int i = 0; i < balls.size(); i++) {
      char c = balls.get(i);
      int val = ballValue.get(i);
      if (c == 'X' || c == '/') {
        if (i + 1 < ballValue.size()) {
          val += ballValue.get(i+1);
        }
      }
      if (c == 'X') {
        if (i + 2 < ballValue.size()) {
          val += ballValue.get(i+2);
        }
      }

      sum += val;

      // if we're in the last frame ignore the bonus balls
      if (i > ballsBeforeLastFrame && (c == 'X' || c == '/')) {
        break;
      }
    }

    return sum;
  }

  public static int getBallScore(char c) {
    if (c == 'X' || c == '/') {
      return 10;
    } else {
      return Character.getNumericValue(c);
    }
  }

}