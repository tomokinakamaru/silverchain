package internal.frontend;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.api.Test;
import silverchain.internal.frontend.Frontend;
import silverchain.internal.frontend.parser.antlr.AgParser.InputContext;

class FrontendTests {

  @Test
  void test() {
    InputContext ctx = new Frontend().run(CharStreams.fromString(""));
    assertThat(ctx).isNotNull();
  }
}
