package silverchain.internal.front.validator;

import java.util.HashMap;
import java.util.Map;
import silverchain.internal.front.parser.AgBaseListener;
import silverchain.internal.front.parser.AgParser.NameContext;
import silverchain.internal.front.parser.AgParser.TypeDeclContext;

public class TypeDeclConflictValidator extends AgBaseListener {

  protected final Map<String, TypeDeclContext> typeDecls = new HashMap<>();

  @Override
  public void enterTypeDecl(TypeDeclContext ctx) {
    String id = stringify(ctx.name());
    if (typeDecls.containsKey(id)) {
      throw new TypeDeclConflict(typeDecls.get(id), ctx);
    } else {
      typeDecls.put(id, ctx);
    }
  }

  protected static String stringify(NameContext ctx) {
    String id = ctx.ID().getText();
    return ctx.name() == null ? id : stringify(ctx.name()) + "." + id;
  }
}
