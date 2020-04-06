package silverchain.generator;

import java.nio.file.Path;

public final class SaveError extends RuntimeException {

  SaveError(Path path) {
    super("Failed to save generated file: " + path);
  }
}
