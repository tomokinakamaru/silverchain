package silverchain.ag.builder;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class SyntaxErrorListener extends BaseErrorListener {

  @Override
  public void syntaxError(Recognizer r, Object o, int l, int c, String m, RecognitionException e) {
    m = m.substring(0, 1).toUpperCase() + m.substring(1);
    throw new SyntaxError(l, c, m);
  }
}
