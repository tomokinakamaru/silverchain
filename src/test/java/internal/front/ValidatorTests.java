package internal.front;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.internal.front.parser.antlr.AgBaseListener;
import silverchain.internal.front.parser.antlr.AgParser.InputContext;
import silverchain.internal.frontend.checker.DuplicateFragment;
import silverchain.internal.frontend.checker.DuplicateFragmentChecker;
import silverchain.internal.frontend.checker.DuplicateType;
import silverchain.internal.frontend.checker.DuplicateTypeChecker;
import silverchain.internal.frontend.checker.ImportConflict;
import silverchain.internal.frontend.checker.ImportConflictChecker;
import silverchain.internal.frontend.checker.InvalidRange;
import silverchain.internal.frontend.checker.InvalidRangeChecker;
import silverchain.internal.frontend.checker.UndefinedFragment;
import silverchain.internal.frontend.checker.UndefinedFragmentChecker;
import silverchain.internal.frontend.checker.ZeroRepeat;
import silverchain.internal.frontend.checker.ZeroRepeatChecker;
import silverchain.internal.frontend.parser.AgParser;

class ValidatorTests {

  private static Arguments[] testData() {
    return new Arguments[] {
      Arguments.of(
          "$FOO = foo(); $FOO = foo();",
          new DuplicateFragmentChecker(),
          DuplicateFragment.class,
          "Duplicate fragment (L1C1 and L1C15)"),
      Arguments.of(
          "Foo { Foo foo(); } Foo { Foo foo(); }",
          new DuplicateTypeChecker(),
          DuplicateType.class,
          "Duplicate type declaration (L1C1 and L1C20)"),
      Arguments.of(
          "foo.Foo { Foo foo(); } foo.Foo { Foo foo(); }",
          new DuplicateTypeChecker(),
          DuplicateType.class,
          "Duplicate type declaration (L1C1 and L1C24)"),
      Arguments.of(
          "import foo.Foo; import bar.Foo;",
          new ImportConflictChecker(),
          ImportConflict.class,
          "Import conflict (L1C1 and L1C17)"),
      Arguments.of(
          "Foo { Foo foo()[2,1]; }",
          new InvalidRangeChecker(),
          InvalidRange.class,
          "min > max (L1C16)"),
      Arguments.of(
          "Foo { Foo $FOO; }",
          new UndefinedFragmentChecker(),
          UndefinedFragment.class,
          "Undefined fragment $FOO (L1C11)"),
      Arguments.of(
          "Foo { Foo foo()[0,0]; }",
          new ZeroRepeatChecker(),
          ZeroRepeat.class,
          "Max is zero (L1C16)")
    };
  }

  @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{3}\"")
  @MethodSource("testData")
  void test(String text, AgBaseListener listener, Class<?> cls, String message) {
    InputContext ctx = new AgParser().parse(CharStreams.fromString(text));
    assertThatThrownBy(() -> ParseTreeWalker.DEFAULT.walk(listener, ctx))
        .isExactlyInstanceOf(cls)
        .hasMessage(message);
  }
}
