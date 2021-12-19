package silverchain.internal.frontend.rewriter;

import static silverchain.internal.frontend.rewriter.utility.TreeReplicator.replicate;

import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgParser.ImportDeclContext;
import silverchain.internal.frontend.antlr.AgParser.NameContext;
import silverchain.internal.frontend.core.AgTreeRewriter;
import silverchain.internal.frontend.core.data.ReturnTypeContext;

@API(status = API.Status.INTERNAL)
public class ImportResolver extends AgTreeRewriter {

  protected Map<String, NameContext> imports = new HashMap<>();

  @Override
  public ParseTree visitImportDecl(ImportDeclContext ctx) {
    NameContext name = ctx.name();
    imports.put(name.ID().getText(), name);
    return null;
  }

  @Override
  public ParseTree visitReturnType(ReturnTypeContext ctx) {
    ReturnTypeContext c = (ReturnTypeContext) super.visitReturnType(ctx);
    if (c != ctx) c.sources().add(c.typeRef().name().start);
    return c;
  }

  @Override
  public ParseTree visitName(NameContext ctx) {
    ctx = (NameContext) super.visitName(ctx);
    if (ctx.qualifier() == null) {
      String id = ctx.ID().getText();
      if (imports.containsKey(id)) {
        return replicate(imports.get(id));
      }
    }
    return ctx;
  }
}
