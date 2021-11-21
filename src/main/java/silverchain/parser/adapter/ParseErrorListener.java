package silverchain.parser.adapter;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public final class ParseErrorListener extends BaseErrorListener {

  public static final ParseErrorListener PARSE_ERROR_LISTENER = new ParseErrorListener();

  private ParseErrorListener() {}

  @Override
  public void syntaxError(
      Recognizer<?, ?> recognizer,
      Object offendingSymbol,
      int line,
      int charPositionInLine,
      String msg,
      RecognitionException e)
      throws ParseCancellationException {
    throw new ParseError("line " + line + ":" + charPositionInLine + " " + msg);
  }
}
