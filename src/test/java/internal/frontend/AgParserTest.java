package internal.frontend;

import static internal.frontend.utility.Functions.parse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.internal.frontend.antlr.AgParser.InputContext;
import silverchain.internal.frontend.builder.SyntaxError;

public class AgParserTest {

  private static Arguments[] data() {
    return new Arguments[] {Arguments.of("", "([] <EOF>)")};
  }

  private static Arguments[] errorData() {
    return new Arguments[] {
      Arguments.of("!", "Syntax error: Token recognition error at: '!' (L1C1)")
    };
  }

  @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{1}\"")
  @MethodSource("data")
  void test(String text, String expected) {
    InputContext ctx = parse(text);
    assertThat(ctx.toStringTree()).isEqualTo(expected);
  }

  @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{1}\"")
  @MethodSource("errorData")
  void errorTest(String text, String message) {
    assertThatThrownBy(() -> parse(text))
        .isExactlyInstanceOf(SyntaxError.class)
        .hasMessage(message);
  }
}
