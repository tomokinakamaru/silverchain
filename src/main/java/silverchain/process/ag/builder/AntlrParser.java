package silverchain.process.ag.builder;

import org.antlr.v4.runtime.TokenStream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class AntlrParser extends silverchain.process.ag.antlr.AgParser {

  public AntlrParser(TokenStream input) {
    super(input);
  }
}
