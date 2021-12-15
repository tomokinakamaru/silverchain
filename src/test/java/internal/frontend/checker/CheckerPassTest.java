package internal.frontend.checker;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.internal.frontend.checker.DuplicateFragmentChecker;
import silverchain.internal.frontend.checker.DuplicateTypeChecker;
import silverchain.internal.frontend.checker.ImportConflictChecker;
import silverchain.internal.frontend.checker.InvalidRangeChecker;
import silverchain.internal.frontend.checker.UndefinedFragmentChecker;
import silverchain.internal.frontend.checker.ZeroRepeatChecker;
import silverchain.internal.frontend.parser.AgParser;
import silverchain.internal.frontend.parser.antlr.AgBaseListener;
import silverchain.internal.frontend.parser.antlr.AgParser.InputContext;

class CheckerPassTest {

  private static Arguments[] data() {
    return new Arguments[] {
      Arguments.of("$FOO = foo(); $BAR = foo();", new DuplicateFragmentChecker()),
      Arguments.of("Foo { Foo foo(); } Bar { Foo foo(); }", new DuplicateTypeChecker()),
      Arguments.of("import foo.Foo; import bar.Bar;", new ImportConflictChecker()),
      Arguments.of("Foo { Foo foo()[1,2]; }", new InvalidRangeChecker()),
      Arguments.of("Foo { Foo foo()[1,]; }", new InvalidRangeChecker()),
      Arguments.of("$FOO = foo(); Foo { Foo $FOO; }", new UndefinedFragmentChecker()),
      Arguments.of("Foo { Foo foo()[1]; }", new ZeroRepeatChecker()),
      Arguments.of("Foo { Foo foo()[0,]; }", new ZeroRepeatChecker()),
      Arguments.of("Foo { Foo foo()[0,1]; }", new ZeroRepeatChecker()),
    };
  }

  @ParameterizedTest(name = "[{index}] \"{0}\"")
  @MethodSource("data")
  void test(String text, AgBaseListener listener) {
    InputContext ctx = new AgParser().parse(CharStreams.fromString(text));
    ParseTreeWalker.DEFAULT.walk(listener, ctx);
  }
}
