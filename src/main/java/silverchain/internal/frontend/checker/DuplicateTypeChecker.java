package silverchain.internal.frontend.checker;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apiguardian.api.API;
import silverchain.internal.frontend.parser.antlr.AgBaseListener;
import silverchain.internal.frontend.parser.antlr.AgParser.NameContext;
import silverchain.internal.frontend.parser.antlr.AgParser.QualifierContext;
import silverchain.internal.frontend.parser.antlr.AgParser.TypeDeclContext;

@API(status = API.Status.INTERNAL)
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
    return stringify(ctx.qualifier()) + ctx.ID().getText();
  }

  protected static String stringify(QualifierContext ctx) {
    if (ctx == null) return "";
    return ctx.ID().stream().map(ParseTree::getText).collect(Collectors.joining(".")) + ".";
  }
}
