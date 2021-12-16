package silverchain.internal.frontend.rewriter;

import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apiguardian.api.API;
import silverchain.internal.frontend.parser.antlr.AgParser.ImportDeclContext;
import silverchain.internal.frontend.parser.antlr.AgParser.NameContext;
import silverchain.internal.frontend.parser.antlr.AgParser.TypeDeclContext;
import silverchain.internal.frontend.parser.antlr.AgParser.TypeRefContext;
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
  public ParseTree visitTypeDecl(TypeDeclContext ctx) {
    ctx = (TypeDeclContext) super.visitTypeDecl(ctx);
    NameContext n = find(ctx.name());
    if (n != null) {
      TypeDeclContext c = (TypeDeclContext) ctx.accept(new ContextReplicator());
      c.children.replaceAll(t -> t == c.name() ? n : t);
      return c;
    }
    return ctx;
  }

  @Override
  public ParseTree visitTypeRef(TypeRefContext ctx) {
    ctx = (TypeRefContext) super.visitTypeRef(ctx);
    NameContext n = find(ctx.name());
    if (n != null) {
      TypeRefContext c = (TypeRefContext) ctx.accept(new ContextReplicator());
      c.children.replaceAll(t -> t == c.name() ? n : t);
      return c;
    }
    return ctx;
  }

  protected NameContext find(NameContext name) {
    if (name.qualifier() == null) {
      String id = name.ID().getText();
      if (imports.containsKey(id)) {
        return imports.get(id);
      }
    }
    return null;
  }
}
