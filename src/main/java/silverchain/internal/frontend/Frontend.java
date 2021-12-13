package silverchain.internal.frontend;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apiguardian.api.API;
import silverchain.internal.frontend.checker.DuplicateFragmentChecker;
import silverchain.internal.frontend.checker.DuplicateTypeChecker;
import silverchain.internal.frontend.checker.ImportConflictChecker;
import silverchain.internal.frontend.checker.InvalidRangeChecker;
import silverchain.internal.frontend.checker.UndefinedFragmentChecker;
import silverchain.internal.frontend.checker.ZeroRepeatChecker;
import silverchain.internal.frontend.parser.AgParser;
import silverchain.internal.frontend.parser.antlr.AgParser.InputContext;
import silverchain.internal.frontend.rewriter.FragmentResolver;
import silverchain.internal.frontend.rewriter.ImportResolver;

@API(status = API.Status.INTERNAL)
public class Frontend {

  public InputContext run(CharStream stream) {
    InputContext ctx = new AgParser().parse(stream);
    ParseTreeWalker.DEFAULT.walk(new ImportConflictChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new DuplicateTypeChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new DuplicateFragmentChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new UndefinedFragmentChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new ZeroRepeatChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new InvalidRangeChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new ImportResolver(), ctx);
    ParseTreeWalker.DEFAULT.walk(new FragmentResolver(), ctx);
    return ctx;
  }
}
