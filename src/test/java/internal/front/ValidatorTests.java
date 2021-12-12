package internal.front;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.internal.front.parser.AgParser;
import silverchain.internal.front.parser.antlr.AgBaseListener;
import silverchain.internal.front.parser.antlr.AgParser.InputContext;
import silverchain.internal.front.validator.DuplicateFragment;
import silverchain.internal.front.validator.DuplicateFragmentValidator;
import silverchain.internal.front.validator.DuplicateTypeDecl;
import silverchain.internal.front.validator.DuplicateTypeDeclValidator;
import silverchain.internal.front.validator.ImportConflict;
import silverchain.internal.front.validator.ImportConflictValidator;
import silverchain.internal.front.validator.InvalidRange;
import silverchain.internal.front.validator.InvalidRangeValidator;
import silverchain.internal.front.validator.UndefinedFragment;
import silverchain.internal.front.validator.UndefinedFragmentValidator;
import silverchain.internal.front.validator.ZeroRepeat;
import silverchain.internal.front.validator.ZeroRepeatValidator;

class ValidatorTests {

  private static Arguments[] testData() {
    return new Arguments[] {
      Arguments.of(
          "$FOO = foo(); $FOO = foo();",
          new DuplicateFragmentValidator(),
          DuplicateFragment.class,
          "Duplicate fragment (L1C1 and L1C15)"),
      Arguments.of(
          "Foo { Foo foo(); } Foo { Foo foo(); }",
          new DuplicateTypeDeclValidator(),
          DuplicateTypeDecl.class,
          "Duplicate type declaration (L1C1 and L1C20)"),
      Arguments.of(
          "foo.Foo { Foo foo(); } foo.Foo { Foo foo(); }",
          new DuplicateTypeDeclValidator(),
          DuplicateTypeDecl.class,
          "Duplicate type declaration (L1C1 and L1C24)"),
      Arguments.of(
          "import foo.Foo; import bar.Foo;",
          new ImportConflictValidator(),
          ImportConflict.class,
          "Import conflict (L1C1 and L1C17)"),
      Arguments.of(
          "Foo { Foo foo()[2,1]; }",
          new InvalidRangeValidator(),
          InvalidRange.class,
          "min > max (L1C16)"),
      Arguments.of(
          "Foo { Foo $FOO; }",
          new UndefinedFragmentValidator(),
          UndefinedFragment.class,
          "Undefined fragment $FOO (L1C11)"),
      Arguments.of(
          "Foo { Foo foo()[0,0]; }",
          new ZeroRepeatValidator(),
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
