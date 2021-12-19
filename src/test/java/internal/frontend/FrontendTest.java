package internal.frontend;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.internal.frontend.Frontend;
import silverchain.internal.frontend.antlr.AgParser.InputContext;

public class FrontendTest {

  private static Arguments[] data() {
    return new Arguments[] {Arguments.of("", "([] <EOF>)")};
  }

  @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{1}\"")
  @MethodSource("data")
  void test(String text, String expected) {
    InputContext ctx = new Frontend().run(CharStreams.fromString(text));
    assertThat(ctx.toStringTree()).isEqualTo(expected);
  }
}
