package silverchain.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class GeneratedFile {

  private final Path path;

  private final String content;

  public GeneratedFile(Path path, String content) {
    this.path = path;
    this.content = content;
  }

  public Path path() {
    return path;
  }

  public String content() {
    return content;
  }

  public void save(Path path) {
    Path p = path.resolve(this.path);
    try {
      Files.createDirectories(p.getParent());
      Files.write(p, content.getBytes());
    } catch (IOException e) {
      throw new SaveError(p);
    }
  }
}
