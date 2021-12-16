package internal.frontend.rewriter;

import static internal.utility.Functions.parse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.internal.frontend.parser.antlr.AgParser.InputContext;
import silverchain.internal.frontend.rewriter.utility.ContextReplicator;

class ReplicatorTest {

  private static Arguments[] data() {
    return new Arguments[] {
      Arguments.of("import foo.Foo; $FOO = foo();"),
      Arguments.of("Foo { Foo {(((foo()*)+)?)[1,2]}; }"),
      Arguments.of("Foo { Foo foo(Foo foo) throws Foo; }"),
      Arguments.of("Foo { Foo $FOO; }"),
      Arguments.of("Foo<T extends Foo> { Foo<Foo,? extends Foo,? super Foo> foo(); }")
    };
  }

  @ParameterizedTest(name = "[{index}] \"{0}\"")
  @MethodSource("data")
  void test(String text) {
    InputContext ctx = parse(text);
    InputContext c = (InputContext) ctx.accept(new ContextReplicator());
    assertThat(ctx.toStringTree()).isEqualTo(c.toStringTree());
  }
}
