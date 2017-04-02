import org.junit.Test;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

public class DictionaryTest {

  @Test
  public void testCustom() {
    System.out.println("Testing");
    // assertEquals("a", Dictionary.replaceChar("b", 0, 'a'));
    // assertEquals("bbcd", Dictionary.replaceChar("abcd", 0, 'b'));
    // assertEquals("abbd", Dictionary.replaceChar("abcd", 2, 'b'));

    assertEquals(0, Dictionary.editDistanceBU("", ""));
    assertEquals(0, Dictionary.editDistanceBU("a", "a"));
    assertEquals(0, Dictionary.editDistanceBU("abcd", "abcd"));
    assertEquals(1, Dictionary.editDistanceBU("a", "ba"));
    assertEquals(1, Dictionary.editDistanceBU("java", "aava"));
    assertEquals(1, Dictionary.editDistanceBU("aava", "java"));
    assertEquals(2, Dictionary.editDistanceBU("java", "aaaa"));
    assertEquals(2, Dictionary.editDistanceBU("aaaa", "java"));
    assertEquals(1, Dictionary.editDistanceBU("java", "ajava"));
    assertEquals(1, Dictionary.editDistanceBU("ajava", "java"));
    assertEquals(1, Dictionary.editDistanceBU("java", "javaa"));
    assertEquals(1, Dictionary.editDistanceBU("javaa", "java"));
    assertEquals(2, Dictionary.editDistanceBU("java", "ajavaa"));
    assertEquals(2, Dictionary.editDistanceBU("ajavaa", "java"));
    assertEquals(4, Dictionary.editDistanceBU("java", "heaven"));
    assertEquals(6, Dictionary.editDistanceBU("cherry", "strawberry"));
    assertEquals(6, Dictionary.editDistanceBU("strawberry", "cherry"));
    assertEquals(4, Dictionary.editDistanceBU("scrawherry", "cherry"));
    assertEquals(4, Dictionary.editDistanceBU("cherry", "scrawherry"));
    assertEquals(4, Dictionary.editDistanceBU("scrawherry", "cherry"));



    assertEquals(1, Dictionary.editDistanceBU("strawberry", "strawbery"));
    assertEquals(1, Dictionary.editDistanceBU("strawbery", "strawberry"));

    // Specifics from the provided tests
    assertEquals(7, Dictionary.editDistanceBU("cherry", "strawbery"));
    assertEquals(7, Dictionary.editDistanceBU("strawbery", "cherry"));
    assertEquals(2, Dictionary.editDistanceBU("cherry", "berry"));

    assertEquals(5, Dictionary.editDistanceBU("raspberry", "strawbery"));
    assertEquals(5, Dictionary.editDistanceBU("strawbery", "raspberry"));
    assertEquals(2, Dictionary.editDistanceBU("cherry", "berry"));

    // this is why this alg doesn't work
    assertEquals(2, Dictionary.editDistanceBU("61234567890", "12345667890"));
    assertEquals(4, Dictionary.editDistanceBU("661234567890", "123456667890"));
    assertEquals(2, Dictionary.editDistanceBU("2121333", "1212333"));
    assertEquals(4, Dictionary.editDistanceBU("661234567890", "123456667890"));


  }

  @Test
  public void testBerries() {
    Dictionary dictionary = new Dictionary(new String[]{"cherry", "pineapple", "melon", "strawberry", "raspberry"});
    assertEquals("strawberry", dictionary.findMostSimilar("strawbery"));
    assertEquals("cherry", dictionary.findMostSimilar("berry"));
  }

  @Test
  public void testLanguages() {
    Dictionary dictionary = new Dictionary(new String[]{"javascript", "java", "ruby", "php", "python", "coffeescript"});
    assertEquals("java", dictionary.findMostSimilar("heaven"));
    assertEquals("javascript", dictionary.findMostSimilar("javascript"));
  }

}