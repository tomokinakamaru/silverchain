package silverchain.process.ag.checker;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.apiguardian.api.API;
import silverchain.data.ag.visitor.AgTreeChecker;
import silverchain.process.ag.antlr.AgParser.NameContext;
import silverchain.process.ag.antlr.AgParser.QualifierContext;
import silverchain.process.ag.antlr.AgParser.TypeDeclContext;
import silverchain.process.ag.checker.exception.DuplicateType;

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
