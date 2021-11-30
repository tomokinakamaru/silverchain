import static org.assertj.core.api.Assertions.assertThat;

import itemization.Itemization;
import org.junit.jupiter.api.Test;

final class ItemizationTest {

  @Test
  void main() {
    String s =
        new Itemization()
            .begin()
            .item("foo")
            .begin()
            .item(1)
            .item(2)
            .end()
            .item("bar")
            .end()
            .toTeX();

    assertThat(s).isEqualTo("\\begin{itemize}\n"
            + "\\item foo\n"
            + "\\begin{itemize}\n"
            + "\\item 1\n"
            + "\\item 2\n"
            + "\\end{itemize}\n"
            + "\\item bar\n"
            + "\\end{itemize}\n");
  }
}
