package internal.frontend;

import org.antlr.v4.runtime.CharStreams;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.internal.frontend.Frontend;
import silverchain.internal.frontend.parser.antlr.AgParser.InputContext;

class FrontendTest {

  private static Arguments[] data() {
    return new Arguments[] {Arguments.of("", "([] <EOF>)")};
  }

  @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{1}\"")
  @MethodSource("data")
  void test(String text, String expected) {
    InputContext ctx = new Frontend().run(CharStreams.fromString(""));
    Assertions.assertThat(ctx.toStringTree()).isEqualTo(expected);
  }
}
