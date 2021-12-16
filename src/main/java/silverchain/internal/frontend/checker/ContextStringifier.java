package silverchain.internal.frontend.checker;

import java.util.stream.Collectors;
import org.apiguardian.api.API;
import silverchain.internal.frontend.parser.antlr.AgParser.NameContext;
import silverchain.internal.frontend.parser.antlr.AgParser.QualifierContext;

@API(status = API.Status.INTERNAL)
public final class ContextStringifier {

  private ContextStringifier() {}

  public static String stringify(NameContext ctx) {
    return stringify(ctx.qualifier()) + ctx.ID().getText();
  }

  public static String stringify(QualifierContext ctx) {
    if (ctx == null) return "";
    return ctx.ID().stream().map(n -> n.getText() + ".").collect(Collectors.joining());
  }
}
