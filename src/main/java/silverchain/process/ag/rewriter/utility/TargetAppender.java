package silverchain.process.ag.rewriter.utility;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apiguardian.api.API;
import silverchain.data.ag.MethodContext;
import silverchain.data.ag.visitor.AgTreeRewriter;

@API(status = API.Status.INTERNAL)
public class TargetAppender extends AgTreeRewriter {

  private final Token token;

  private TargetAppender(Token token) {
    this.token = token;
  }

  @SuppressWarnings("unchecked")
  public static <T extends ParserRuleContext> T append(T ctx, Token token) {
    return (T) ctx.accept(new TargetAppender(token));
  }

  @Override
  public ParseTree visitMethod(MethodContext ctx) {
    MethodContext c = (MethodContext) build(MethodContext::new, ctx, rewriteChildren(ctx));
    c.targets().addAll(ctx.targets());
    c.targets().add(token);
    return c;
  }
}
