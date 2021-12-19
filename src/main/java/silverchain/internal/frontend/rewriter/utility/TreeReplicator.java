package silverchain.internal.frontend.rewriter.utility;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apiguardian.api.API;
import silverchain.internal.frontend.core.AgTreeRewriter;
import silverchain.internal.frontend.core.TreeConstructor;

@API(status = API.Status.INTERNAL)
public class TreeReplicator extends AgTreeRewriter {

  private static final TreeReplicator REPLICATOR = new TreeReplicator();

  private TreeReplicator() {}

  @SuppressWarnings("unchecked")
  public static <T extends ParserRuleContext> T replicate(T ctx) {
    return (T) ctx.accept(REPLICATOR);
  }

  @Override
  protected ParseTree build(TreeConstructor f, ParserRuleContext ctx) {
    return build(f, ctx, rewriteChildren(ctx));
  }
}
