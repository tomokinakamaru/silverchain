package internal.front;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.internal.frontend.parser.AgParser;
import silverchain.internal.frontend.parser.SyntaxError;

class AgParserTests {

  private static Arguments[] testSyntaxErrorData() {
    return new Arguments[] {
      Arguments.of("!", "Syntax error: token recognition error at: '!' (L1C1)")
    };
  }

  @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{1}\"")
  @MethodSource("testSyntaxErrorData")
  void testSyntaxError(String text, String message) {
    AgParser parser = new AgParser();
    CharStream stream = CharStreams.fromString(text);
    assertThatThrownBy(() -> parser.parse(stream))
        .isExactlyInstanceOf(SyntaxError.class)
        .hasMessage(message);
  }

  @Test
  void testNoError() {
    new AgParser().parse(CharStreams.fromString(""));
  }
}
