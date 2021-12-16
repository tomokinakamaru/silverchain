package internal.frontend.parser;

import static internal.utility.Functions.parse;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.internal.frontend.parser.SyntaxError;

class AgParserErrorTest {

  private static Arguments[] data() {
    return new Arguments[] {
      Arguments.of("!", "Syntax error: Token recognition error at: '!' (L1C1)")
    };
  }

  @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{1}\"")
  @MethodSource("data")
  void test(String text, String message) {
    assertThatThrownBy(() -> parse(text))
        .isExactlyInstanceOf(SyntaxError.class)
        .hasMessage(message);
  }
}
