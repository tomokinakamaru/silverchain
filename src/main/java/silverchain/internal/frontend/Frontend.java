package silverchain.internal.frontend;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import silverchain.internal.frontend.checker.DuplicateFragmentChecker;
import silverchain.internal.frontend.checker.DuplicateTypeChecker;
import silverchain.internal.frontend.checker.ImportConflictChecker;
import silverchain.internal.frontend.checker.InvalidRangeChecker;
import silverchain.internal.frontend.checker.UndefinedFragmentChecker;
import silverchain.internal.frontend.checker.ZeroRepeatChecker;
import silverchain.internal.frontend.parser.AgParser;
import silverchain.internal.frontend.parser.antlr.AgParser.InputContext;
import silverchain.internal.frontend.rewriter.FragmentExpander;
import silverchain.internal.frontend.rewriter.ImportExpander;

public class Frontend {

  public InputContext run(CharStream stream) {
    InputContext ctx = new AgParser().parse(stream);
    ParseTreeWalker.DEFAULT.walk(new ImportConflictChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new DuplicateTypeChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new DuplicateFragmentChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new UndefinedFragmentChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new ZeroRepeatChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new InvalidRangeChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new ImportExpander(), ctx);
    ParseTreeWalker.DEFAULT.walk(new FragmentExpander(), ctx);
    return ctx;
  }
}
