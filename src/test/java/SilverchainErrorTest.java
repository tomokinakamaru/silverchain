import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.Silverchain;
import silverchain.SilverchainException;

final class SilverchainErrorTest {

  private static final Path OUTPUT = Paths.get("build/silverchain/null");

  private static Arguments[] data() {
    // @formatter:off
    return array(
        args(
            "!",
            "Unexpected token: \"!\" (L1C1)"),
        args(
            "Foo<",
            "Unexpected token: \"<EOF>\" (L1C5)"),
        args(
            "$FOO = foo(); $FOO = foo();",
            "Duplicate fragment declaration: $FOO (L1C1 and L1C15)"),
        args(
            "Foo { Foo foo(); } Foo { Foo foo(); }",
            "Duplicate type declaration: Foo (L1C1 and L1C20)"),
        args(
            "foo.Foo { Foo foo(); } foo.Foo { Foo foo(); }",
            "Duplicate type declaration: foo.Foo (L1C1 and L1C24)"),
        args(
            "import foo.Foo; import bar.Foo;",
            "Alias conflict: Foo (L1C1 and L1C17)"),
        args(
            "Foo { Foo foo()[2,1]; }",
            "Invalid range: min=2 > max=1 (L1C16)"),
        args(
            "Foo { Foo $FOO; }",
            "Undefined fragment: $FOO (L1C11)"),
        args(
            "Foo { Foo foo()[0]; }",
            "Max is zero (L1C16)"),
        args(
            "Foo { Foo foo()[0,0]; }",
            "Max is zero (L1C16)"),
        args(
            "Foo { Foo foo()*; }",
            "Conflict: Foo@L1C7; foo()@L1C11"),
        args(
            "Foo { Foo foo(); Bar foo(); }",
            "Conflict: Bar@L1C18; Foo@L1C7"));
    // @formatter:on
  }

  @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{1}\"")
  @MethodSource("data")
  void test(String src, String message) {
    Silverchain silverchain = new Silverchain();
    silverchain.setOutput(OUTPUT.toString());
    assertThatThrownBy(() -> silverchain.run(src))
        .isInstanceOf(SilverchainException.class)
        .hasMessage(message);
  }

  private static Arguments[] array(Arguments... args) {
    return args;
  }

  private static Arguments args(Object... args) {
    return Arguments.of(args);
  }
}
