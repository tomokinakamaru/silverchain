package internal.frontend.parser;

import static internal.utility.Functions.parse;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AgParserTest {

  private static Arguments[] data() {
    return new Arguments[] {Arguments.of("", "([] <EOF>)")};
  }

  @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{1}\"")
  @MethodSource("data")
  void test(String text, String expected) {
    assertThat(parse(text).toStringTree()).isEqualTo(expected);
  }
}
