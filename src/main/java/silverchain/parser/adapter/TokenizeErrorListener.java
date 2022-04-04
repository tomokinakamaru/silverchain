package silverchain.parser.adapter;

import static silverchain.parser.adapter.Utility.buildErrorMessage;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class TokenizeErrorListener extends BaseErrorListener {

  public static final TokenizeErrorListener TOKENIZE_ERROR_LISTENER = new TokenizeErrorListener();

  private TokenizeErrorListener() {}

  @Override
  public void syntaxError(
      Recognizer<?, ?> recognizer,
      Object offendingSymbol,
      int line,
      int charPositionInLine,
      String msg,
      RecognitionException e)
      throws ParseCancellationException {
    throw new TokenizeError(buildErrorMessage(msg, line, charPositionInLine));
  }
}
