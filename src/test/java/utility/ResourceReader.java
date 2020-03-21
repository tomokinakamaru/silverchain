package utility;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ResourceReader {

  private static final Path resources = Paths.get("src").resolve("test").resolve("resources");

  private final Path path;

  public ResourceReader(String... names) {
    path = resolve(resources, names);
  }

  public InputStream read(String fileName) {
    try {
      return new ByteArrayInputStream(Files.readAllBytes(resolve(path, fileName)));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<File> list(String... names) {
    Path p = resolve(path, names);
    File[] files = p.toFile().listFiles();
    return files == null ? Collections.emptyList() : Arrays.asList(files);
  }

  private static Path resolve(Path path, String... names) {
    for (String name : names) {
      path = path.resolve(name);
    }
    return path;
  }
}
