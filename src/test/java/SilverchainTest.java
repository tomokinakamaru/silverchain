import static utility.FileDeleter.delete;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import silverchain.Silverchain;
import silverchain.parser.ParseException;

final class SilverchainTest {

  private static final Path outputDirectory = Paths.get("build").resolve("silverchain");

  @Test
  void test() throws IOException, ParseException {
    Silverchain silverchain = new Silverchain();
    silverchain.output(outputDirectory);
    silverchain.run(new ByteArrayInputStream("Foo: foo() Foo;".getBytes()));
  }

  @BeforeEach
  void clean() {
    delete(outputDirectory);
  }
}
