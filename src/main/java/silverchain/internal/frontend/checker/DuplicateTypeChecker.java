package silverchain.internal.frontend.checker;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgParser.NameContext;
import silverchain.internal.frontend.antlr.AgParser.QualifierContext;
import silverchain.internal.frontend.antlr.AgParser.TypeDeclContext;
import silverchain.internal.frontend.checker.exception.DuplicateType;
import silverchain.internal.frontend.core.AgTreeChecker;

@API(status = API.Status.INTERNAL)
public class DuplicateTypeChecker extends AgTreeChecker {

  protected Map<String, TypeDeclContext> declarations = new HashMap<>();

  @Override
  public void enterTypeDecl(TypeDeclContext ctx) {
    String id = stringify(ctx.name());
    if (declarations.containsKey(id)) {
      throw new DuplicateType(id, declarations.get(id), ctx);
    } else {
      declarations.put(id, ctx);
    }
  }

  protected String stringify(NameContext ctx) {
    return stringify(ctx.qualifier()) + ctx.ID().getText();
  }

  protected String stringify(QualifierContext ctx) {
    if (ctx == null) return "";
    return ctx.ID().stream().map(n -> n.getText() + ".").collect(Collectors.joining());
  }
}
