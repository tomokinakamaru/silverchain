package silverchain.ag.builder;

import org.antlr.v4.runtime.CharStream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class AgLexer extends silverchain.ag.antlr.AgLexer {

  public AgLexer(CharStream input) {
    super(input);
    removeErrorListeners();
    addErrorListener(new SyntaxErrorListener());
  }
}
