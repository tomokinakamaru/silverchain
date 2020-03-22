package codegen;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import silverchain.codegen.GeneratedFile;

public final class GeneratedFileTester {

  private static final Path resources = Paths.get("src").resolve("test").resolve("resources");

  private final Path path;

  public GeneratedFileTester(String name, String... names) {
    this.path = resources.resolve(Paths.get(name, names));
  }

  @SuppressWarnings("ConstantConditions")
  public void test(List<GeneratedFile> files) {
    files.forEach(this::test);

    Set<Path> genPaths = new HashSet<>();
    files.forEach(f -> genPaths.add(f.path()));
    for (File f : path.toFile().listFiles()) {
      if (f.isFile()) {
        Path p = path.relativize(f.toPath());
        assert genPaths.contains(p);
      }
    }
  }

  private void test(GeneratedFile file) {
    String generated = file.content();
    String expected = read(path.resolve(file.path())).trim();
    assert generated.equals(expected);
  }

  private static String read(Path path) {
    try {
      return new String(Files.readAllBytes(path));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
