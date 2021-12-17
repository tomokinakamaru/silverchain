package silverchain.internal.frontend.rewriter;

import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgParser.ImportDeclContext;
import silverchain.internal.frontend.antlr.AgParser.NameContext;
import silverchain.internal.frontend.antlr.AgParser.ReturnTypeContext;
import silverchain.internal.frontend.data.ReturnTypeCtx;
import silverchain.internal.frontend.utility.ContextReplicator;
import silverchain.internal.frontend.utility.ContextRewriter;

@API(status = API.Status.INTERNAL)
public class ImportResolver extends ContextRewriter {

  protected final Map<String, NameContext> imports = new HashMap<>();

  @Override
  public ParseTree visitImportDecl(ImportDeclContext ctx) {
    NameContext name = ctx.name();
    imports.put(name.ID().getText(), name);
    return null;
  }

  @Override
  public ParseTree visitName(NameContext ctx) {
    ctx = (NameContext) super.visitName(ctx);
    if (ctx.qualifier() == null) {
      String id = ctx.ID().getText();
      if (imports.containsKey(id)) {
        return imports.get(id).accept(new ContextReplicator());
      }
    }
    return ctx;
  }
}
