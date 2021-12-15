package internal.frontend.parser;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.internal.frontend.parser.AgParser;
import silverchain.internal.frontend.parser.SyntaxError;

class AgParserErrorTest {

  private static Arguments[] data() {
    return new Arguments[] {
      Arguments.of("!", "Syntax error: token recognition error at: '!' (L1C1)")
    };
  }

  @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{1}\"")
  @MethodSource("data")
  void test(String text, String message) {
    AgParser parser = new AgParser();
    CharStream stream = CharStreams.fromString(text);
    assertThatThrownBy(() -> parser.parse(stream))
        .isExactlyInstanceOf(SyntaxError.class)
        .hasMessage(message);
  }
}
