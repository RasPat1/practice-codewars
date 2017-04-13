import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

public class MixingTest {

    @Test
    public void test() {
        System.out.println("Mix Fixed Tests");
        assertEquals("2:eeeee/2:yy/=:hh/=:rr", Mixing.mix("Are they here", "yes, they are here"));
        assertEquals("1:ooo/1:uuu/2:sss/=:nnn/1:ii/2:aa/2:dd/2:ee/=:gg",
                Mixing.mix("looping is fun but dangerous", "less dangerous than coding"));
        assertEquals("1:aaa/1:nnn/1:gg/2:ee/2:ff/2:ii/2:oo/2:rr/2:ss/2:tt",
                Mixing.mix(" In many languages", " there's a pair of functions"));
        assertEquals("1:ee/1:ll/1:oo", Mixing.mix("Lords of the Fallen", "gamekult"));
        assertEquals("", Mixing.mix("codewars", "codewars"));
        assertEquals("1:nnnnn/1:ooooo/1:tttt/1:eee/1:gg/1:ii/1:mm/=:rr",
                Mixing.mix("A generation must confront the looming ", "codewarrs"));
    }

    @Test
    public void testCounts() {
      String s = "ABCDFGHIaaabbccccddef";
      Map<Character, Integer> map = Mixing.getCounts(s);
      assertEquals(3, (int)map.get('a'));
      assertEquals(2, (int)map.get('b'));
      assertEquals(4, (int)map.get('c'));
      assertEquals(2, (int)map.get('d'));
      assertEquals(1, (int)map.get('e'));
      assertEquals(1, (int)map.get('f'));
    }

    @Test
    public void testMixers() {
      String s1 = "ABCDFGHIaaaccccddef";
      String s2 = "ABCHIaabbcccddef";
      Map<Character, Integer> map1 = Mixing.getCounts(s1);
      Map<Character, Integer> map2 = Mixing.getCounts(s2);
      List<MixHelper> mixes = new ArrayList<MixHelper>();

      Mixing.fillHelperFromMap(map1, map2, mixes, '1');
      assertEquals(3, mixes.size());
      Mixing.fillHelperFromMap(map2, map1, mixes, '2');
      assertEquals(4, mixes.size());

      assertEquals("1:aaa", mixes.get(0).toString());
      assertEquals("1:cccc", mixes.get(1).toString());
      assertEquals("=:dd", mixes.get(2).toString());
      assertEquals("2:bb", mixes.get(3).toString());
    }

    @Test
    public void testMixers2() {
      String s1 = "Are they here";
      String s2 = "yes, they are here";
      Map<Character, Integer> map1 = Mixing.getCounts(s1);
      Map<Character, Integer> map2 = Mixing.getCounts(s2);
      List<MixHelper> mixes = new ArrayList<MixHelper>();

      Mixing.fillHelperFromMap(map1, map2, mixes, '1');
      Mixing.fillHelperFromMap(map2, map1, mixes, '2');
      assertEquals(4, mixes.size());

      assertEquals("=:rr", mixes.get(0).toString());
      assertEquals("2:eeeee", mixes.get(1).toString());
      assertEquals("=:hh", mixes.get(2).toString());
      assertEquals("2:yy", mixes.get(3).toString());
    }

}