import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static utility.Functions.parse;
import static utility.Functions.walk;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.process.ag.antlr.AgBaseListener;
import silverchain.process.ag.checker.DuplicateFragmentChecker;
import silverchain.process.ag.checker.DuplicateTypeChecker;
import silverchain.process.ag.checker.ImportConflictChecker;
import silverchain.process.ag.checker.InvalidRepeatChecker;
import silverchain.process.ag.checker.UndefinedFragmentChecker;
import silverchain.process.ag.checker.ZeroRepeatChecker;
import silverchain.process.ag.checker.exception.DuplicateFragment;
import silverchain.process.ag.checker.exception.DuplicateType;
import silverchain.process.ag.checker.exception.ImportConflict;
import silverchain.process.ag.checker.exception.InvalidRepeat;
import silverchain.process.ag.checker.exception.UndefinedFragment;
import silverchain.process.ag.checker.exception.ZeroRepeat;

public class ContextCheckerTest {

  private static Arguments[] data() {
    return new Arguments[] {
      Arguments.of(
          "$FOO = foo(); $FOO = foo();",
          new DuplicateFragmentChecker(),
          DuplicateFragment.class,
          "Duplicate fragment declaration: $FOO (L1C1 and L1C15)"),
      Arguments.of(
          "Foo { Foo foo(); } Foo { Foo foo(); }",
          new DuplicateTypeChecker(),
          DuplicateType.class,
          "Duplicate type declaration: Foo (L1C1 and L1C20)"),
      Arguments.of(
          "foo.Foo { Foo foo(); } foo.Foo { Foo foo(); }",
          new DuplicateTypeChecker(),
          DuplicateType.class,
          "Duplicate type declaration: foo.Foo (L1C1 and L1C24)"),
      Arguments.of(
          "import foo.Foo; import bar.Foo;",
          new ImportConflictChecker(),
          ImportConflict.class,
          "Import conflict: Foo (L1C1 and L1C17)"),
      Arguments.of(
          "Foo { Foo foo()[2,1]; }",
          new InvalidRepeatChecker(),
          InvalidRepeat.class,
          "min=2 > max=1 (L1C16)"),
      Arguments.of(
          "Foo { Foo $FOO; }",
          new UndefinedFragmentChecker(),
          UndefinedFragment.class,
          "Undefined fragment: $FOO (L1C11)"),
      Arguments.of(
          "Foo { Foo foo()[0]; }",
          new ZeroRepeatChecker(),
          ZeroRepeat.class,
          "Max is zero (L1C16)"),
      Arguments.of(
          "Foo { Foo foo()[0,0]; }",
          new ZeroRepeatChecker(),
          ZeroRepeat.class,
          "Max is zero (L1C16)")
    };
  }

  private static Arguments[] passData() {
    return new Arguments[] {
      Arguments.of("$FOO = foo(); $BAR = foo();", new DuplicateFragmentChecker()),
      Arguments.of("Foo { Foo foo(); } Bar { Foo foo(); }", new DuplicateTypeChecker()),
      Arguments.of("import foo.Foo; import bar.Bar;", new ImportConflictChecker()),
      Arguments.of("Foo { Foo foo()[1,2]; }", new InvalidRepeatChecker()),
      Arguments.of("Foo { Foo foo()[1,]; }", new InvalidRepeatChecker()),
      Arguments.of("$FOO = foo(); Foo { Foo $FOO; }", new UndefinedFragmentChecker()),
      Arguments.of("Foo { Foo foo()[1]; }", new ZeroRepeatChecker()),
      Arguments.of("Foo { Foo foo()[0,]; }", new ZeroRepeatChecker()),
      Arguments.of("Foo { Foo foo()[0,1]; }", new ZeroRepeatChecker()),
    };
  }

  @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{3}\"")
  @MethodSource("data")
  void test(String text, AgBaseListener listener, Class<?> cls, String message) {
    assertThatThrownBy(() -> walk(listener, parse(text)))
        .isExactlyInstanceOf(cls)
        .hasMessage(message);
  }

  @ParameterizedTest(name = "[{index}] \"{0}\"")
  @MethodSource("passData")
  void passTest(String text, AgBaseListener listener) {
    walk(listener, parse(text));
  }
}
