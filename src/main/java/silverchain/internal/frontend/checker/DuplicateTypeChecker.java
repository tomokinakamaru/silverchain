package silverchain.internal.frontend.checker;

import java.util.HashMap;
import java.util.Map;
import silverchain.internal.frontend.parser.antlr.AgBaseListener;
import silverchain.internal.frontend.parser.antlr.AgParser.NameContext;
import silverchain.internal.frontend.parser.antlr.AgParser.TypeDeclContext;

public class DuplicateTypeChecker extends AgBaseListener {

  protected final Map<String, TypeDeclContext> declarations = new HashMap<>();

  @Override
  public void enterTypeDecl(TypeDeclContext ctx) {
    String id = stringify(ctx.name());
    if (declarations.containsKey(id)) {
      throw new DuplicateType(declarations.get(id), ctx);
    } else {
      declarations.put(id, ctx);
    }
  }

  protected static String stringify(NameContext ctx) {
    String id = ctx.ID().getText();
    return ctx.name() == null ? id : stringify(ctx.name()) + "." + id;
  }
}
