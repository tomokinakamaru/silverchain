package silverchain.internal.frontend;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgParser.InputContext;
import silverchain.internal.frontend.builder.AgParser;
import silverchain.internal.frontend.builder.AgTreeBuilder;
import silverchain.internal.frontend.checker.DuplicateFragmentChecker;
import silverchain.internal.frontend.checker.DuplicateTypeChecker;
import silverchain.internal.frontend.checker.ImportConflictChecker;
import silverchain.internal.frontend.checker.InvalidRepeatChecker;
import silverchain.internal.frontend.checker.UndefinedFragmentChecker;
import silverchain.internal.frontend.checker.ZeroRepeatChecker;
import silverchain.internal.frontend.rewriter.FragmentResolver;
import silverchain.internal.frontend.rewriter.ImportResolver;
import silverchain.internal.frontend.rewriter.PermutationRewriter;
import silverchain.internal.frontend.rewriter.RepeatRewriter;

@API(status = API.Status.INTERNAL)
public class Frontend {

  public InputContext run(CharStream stream) {
    return rewrite(check(build(stream)));
  }

  protected InputContext build(CharStream stream) {
    return (InputContext) new AgParser().parse(stream).accept(new AgTreeBuilder());
  }

  protected InputContext check(InputContext ctx) {
    ParseTreeWalker.DEFAULT.walk(new ImportConflictChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new DuplicateTypeChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new DuplicateFragmentChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new UndefinedFragmentChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new ZeroRepeatChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new InvalidRepeatChecker(), ctx);
    return ctx;
  }

  protected InputContext rewrite(InputContext ctx) {
    return (InputContext)
        ctx.accept(new ImportResolver())
            .accept(new FragmentResolver())
            .accept(new RepeatRewriter())
            .accept(new PermutationRewriter());
  }
}
