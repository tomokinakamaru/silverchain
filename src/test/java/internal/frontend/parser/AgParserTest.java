package internal.frontend.parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.internal.frontend.parser.AgParser;
import silverchain.internal.frontend.parser.antlr.AgParser.InputContext;

class AgParserTest {

  private static Arguments[] data() {
    return new Arguments[] {Arguments.of("", "([] <EOF>)")};
  }

  @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{1}\"")
  @MethodSource("data")
  void test(String text, String expected) {
    InputContext ctx = new AgParser().parse(CharStreams.fromString(""));
    assertThat(ctx.toStringTree()).isEqualTo(expected);
  }
}
