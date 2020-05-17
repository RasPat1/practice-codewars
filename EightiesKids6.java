import java.util.Map;

public class EightiesKids6 {

  public static String fight(Robot robot1, Robot robot2, Map<String, Integer> tactics) {
    Robot r1 = robot2.getSpeed() > robot1.getSpeed() ? robot2 : robot1;
    Robot r2 = fristActor != robot1 ? robot1 : robot2;

    int r1Damage = 0;
    int r2Damage = 0;

    int r1MoveIndex = 0;
    int r2MoveIndex = 0;

    Robot winner = null;

    // stop looping when veryone has used all their moves
    while (r1MoveIndex >= r1.getTactics().length && r2MoveIndex >= r2.getTactics().length) {
      if (r1MoveIndex < r1.getTactics().length) {
        r1Damage += tactics.get(r1.getTactics()[r1MoveIndex]);
        r1MoveIndex++;
        if (r1Damage >= r2.getHealth()) {
          winner = r1;
          break;
        }
      }

      if (r2MoveIndex < r2.getTactics().length) {
        r2Damage += tactics.get(r2.getTactics()[r2MoveIndex]);
        r2MoveIndex++
        if (r2Damage >= r1.getHealth()) {
          winner = r2;
          break;
        }
      }
    }

    if (winner == null) {
      winner = (r2.getHealth() - r1Damage) > (r1.getHealth() - r2Damage) ? r2 : r1;
    }

    return winner.getName();
  }
}