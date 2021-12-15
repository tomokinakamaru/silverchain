package silverchain.internal.frontend.checker;

import java.util.HashMap;
import java.util.Map;
import org.apiguardian.api.API;
import silverchain.internal.frontend.parser.antlr.AgBaseListener;
import silverchain.internal.frontend.parser.antlr.AgParser.TypeDeclContext;

@API(status = API.Status.INTERNAL)
public class DuplicateTypeChecker extends AgBaseListener {

  protected final Map<String, TypeDeclContext> declarations = new HashMap<>();

  @Override
  public void enterTypeDecl(TypeDeclContext ctx) {
    String id = ContextStringifier.stringify(ctx.name());
    if (declarations.containsKey(id)) {
      throw new DuplicateType(declarations.get(id), ctx);
    } else {
      declarations.put(id, ctx);
    }
  }
}
