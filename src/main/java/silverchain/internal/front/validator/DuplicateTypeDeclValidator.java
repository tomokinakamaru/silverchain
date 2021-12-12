package silverchain.internal.front.validator;

import java.util.HashMap;
import java.util.Map;
import silverchain.internal.front.parser.antlr.AgBaseListener;
import silverchain.internal.front.parser.antlr.AgParser.NameContext;
import silverchain.internal.front.parser.antlr.AgParser.TypeDeclContext;

public class DuplicateTypeDeclValidator extends AgBaseListener {

  protected final Map<String, TypeDeclContext> declarations = new HashMap<>();

  @Override
  public void enterTypeDecl(TypeDeclContext ctx) {
    String id = stringify(ctx.name());
    if (declarations.containsKey(id)) {
      throw new DuplicateTypeDecl(declarations.get(id), ctx);
    } else {
      declarations.put(id, ctx);
    }
  }

  protected static String stringify(NameContext ctx) {
    String id = ctx.ID().getText();
    return ctx.name() == null ? id : stringify(ctx.name()) + "." + id;
  }
}
