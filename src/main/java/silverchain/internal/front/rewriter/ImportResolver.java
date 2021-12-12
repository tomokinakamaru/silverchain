package silverchain.internal.front.rewriter;

import static silverchain.internal.front.rewriter.RewriteUtils.copy;
import static silverchain.internal.front.rewriter.RewriteUtils.pushTokens;
import static silverchain.internal.front.rewriter.RewriteUtils.replaceChild;

import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.ParserRuleContext;
import silverchain.internal.front.parser.antlr.AgBaseListener;
import silverchain.internal.front.parser.antlr.AgParser.ImportDeclContext;
import silverchain.internal.front.parser.antlr.AgParser.NameContext;
import silverchain.internal.front.parser.antlr.AgParser.TypeDeclContext;
import silverchain.internal.front.parser.antlr.AgParser.TypeRefContext;

public class ImportResolver extends AgBaseListener {

  protected final Map<String, NameContext> imports = new HashMap<>();

  @Override
  public void exitImportDecl(ImportDeclContext ctx) {
    NameContext name = ctx.name();
    imports.put(name.ID().getText(), name);
  }

  @Override
  public void exitTypeDecl(TypeDeclContext ctx) {
    replace(ctx, ctx.name());
  }

  @Override
  public void exitTypeRef(TypeRefContext ctx) {
    replace(ctx, ctx.name());
  }

  protected void replace(ParserRuleContext ctx, NameContext oldCtx) {
    if (oldCtx.name() == null) {
      String id = oldCtx.ID().getText();
      if (imports.containsKey(id)) {
        NameContext refCtx = imports.get(oldCtx.ID().getText());
        NameContext newCtx = copy(refCtx);
        pushTokens(oldCtx, newCtx);
        replaceChild(ctx, oldCtx, newCtx);
      }
    }
  }
}
