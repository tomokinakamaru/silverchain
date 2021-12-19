package silverchain.internal.frontend.builder;

import org.antlr.v4.runtime.TokenStream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class AntlrParser extends silverchain.internal.frontend.antlr.AgParser {

  public AntlrParser(TokenStream input) {
    super(input);
  }
}
