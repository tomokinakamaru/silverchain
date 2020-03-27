import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import silverchain.Silverchain;
import silverchain.parser.ParseException;

final class SilverchainTest {

  private static final Path outputDirectory = Paths.get("build").resolve("silverchain");

  @Test
  void test() throws IOException, ParseException {
    Silverchain silverchain = new Silverchain();
    silverchain.outputDirectory(outputDirectory);
    silverchain.run(new ByteArrayInputStream("Foo: foo() Foo;".getBytes()));
  }

  @BeforeAll
  static void clean() {
    if (!delete(outputDirectory.toFile())) {
      throw new RuntimeException("Failed to clean " + outputDirectory.toString());
    }
  }

  private static boolean delete(File file) {
    if (!file.exists()) {
      return true;
    }
    File[] files = file.listFiles();
    if (files != null) {
      for (File f : files) {
        delete(f);
      }
    }
    return file.delete();
  }
}
