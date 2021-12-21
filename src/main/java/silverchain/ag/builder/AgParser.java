package silverchain.ag.builder;

import org.antlr.v4.runtime.TokenStream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class AgParser extends silverchain.ag.antlr.AgParser {

  public AgParser(TokenStream input) {
    super(input);
    removeErrorListeners();
    addErrorListener(new SyntaxErrorListener());
  }
}
