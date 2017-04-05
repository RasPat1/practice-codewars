import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;
import java.util.Random;


public class BowlingTest {

    @Test
    public void BasicTests() {
        // assertEquals("expected", "actual");
        System.out.println("Maybe this bowler should put bumpers on...\n ");
        assertEquals(20, Bowling.bowling_score("11 11 11 11 11 11 11 11 11 11"));

        assertEquals(29, Bowling.bowling_score("1/ 11 11 11 11 11 11 11 11 11"));
        assertEquals(48, Bowling.bowling_score("1/ X 11 11 11 11 11 11 11 11"));
        assertEquals(150, Bowling.bowling_score("5/ 4/ 3/ 2/ 1/ 0/ X 9/ 4/ 8/8"));
        assertEquals(123, Bowling.bowling_score("51 02 8/ 03 X 17 8/ 8/ 7/ XX1"));
        assertEquals(171, Bowling.bowling_score("X X 9/ 80 X X 90 8/ 7/ 44"));



        System.out.println("Woah! Perfect game!");
        assertEquals(300, Bowling.bowling_score("X X X X X X X X X XXX"));
    }
}