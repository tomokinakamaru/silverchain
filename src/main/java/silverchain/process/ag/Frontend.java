package silverchain.process.ag;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apiguardian.api.API;
import silverchain.process.ag.antlr.AgParser.InputContext;
import silverchain.process.ag.builder.AgParser;
import silverchain.process.ag.builder.AgTreeBuilder;
import silverchain.process.ag.checker.DuplicateFragmentChecker;
import silverchain.process.ag.checker.DuplicateTypeChecker;
import silverchain.process.ag.checker.ImportConflictChecker;
import silverchain.process.ag.checker.InvalidRepeatChecker;
import silverchain.process.ag.checker.UndefinedFragmentChecker;
import silverchain.process.ag.checker.ZeroRepeatChecker;
import silverchain.process.ag.rewriter.FragmentResolver;
import silverchain.process.ag.rewriter.ImportResolver;
import silverchain.process.ag.rewriter.PermutationRewriter;
import silverchain.process.ag.rewriter.RepeatRewriter;

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
