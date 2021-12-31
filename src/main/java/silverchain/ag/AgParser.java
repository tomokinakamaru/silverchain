package silverchain.ag;

import org.apiguardian.api.API;
import silverchain.ag.data.DeclsTree;
import silverchain.ag.error.SyntaxError;
import silverchain.ag.javacc.GeneratedParser;
import silverchain.ag.javacc.ParseException;
import silverchain.ag.javacc.StringProvider;

@API(status = API.Status.INTERNAL)
public class AgParser {

  public DeclsTree parse(String text) {
    try {
      return new GeneratedParser(new StringProvider(text)).decls();
    } catch (ParseException e) {
      throw new SyntaxError(e);
    }
  }
}
