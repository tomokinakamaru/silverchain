package silverchain.internal.frontend.builder;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class SyntaxErrorListener extends BaseErrorListener {

  @Override
  public void syntaxError(
      Recognizer<?, ?> recognizer,
      Object offendingSymbol,
      int line,
      int charPositionInLine,
      String msg,
      RecognitionException e) {
    String m = msg.substring(0, 1).toUpperCase() + msg.substring(1);
    throw new SyntaxError(line, charPositionInLine, m);
  }
}
