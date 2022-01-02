package silverchain.ag.error;

import com.github.javaparser.utils.StringEscapeUtils;
import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.ag.javacc.ParseException;
import silverchain.ag.javacc.Token;
import silverchain.srcmap.Position;

@API(status = API.Status.INTERNAL)
public class SyntaxError extends SilverchainException {

  public static String FORMAT = "Unexpected token: \"%s\" (%s)";

  public SyntaxError(ParseException e) {
    super(FORMAT, tokenText(e), position(e));
  }

  protected static String tokenText(ParseException e) {
    Token t = e.currentToken.next;
    return t.image.isEmpty() ? e.tokenImage[t.kind] : StringEscapeUtils.escapeJava(t.image);
  }

  protected static Position position(ParseException e) {
    Token t = e.currentToken.next;
    return new Position(t.beginLine, t.beginColumn + (t.image.isEmpty() ? 1 : 0));
  }
}
