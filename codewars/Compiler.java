import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Compiler {
  public List<String> compile(String prog) {
    return pass3(pass2(pass1(prog)));
  }

  /**
   * Returns an un-optimized AST
   */
  public Ast pass1(String prog) {
    Deque<String> tokens = tokenize(prog);

    return null;
  }

  /**
   * Returns an AST with constant expressions reduced
   */
  public Ast pass2(Ast ast) {
    return null;
  }

  /**
   * Returns assembly instructions
   */
  public List<String> pass3(Ast ast) {
    return null;
  }

  private static Deque<String> tokenize(String prog) {
    Deque<String> tokens = new LinkedList<>();
    Pattern pattern = Pattern.compile("[-+*/()\\[\\]]|[a-zA-Z]+|\\d+");
    Matcher m = pattern.matcher(prog);
    while (m.find()) {
      tokens.add(m.group());
    }
    tokens.add("$"); // end-of-stream
    return tokens;
  }
}