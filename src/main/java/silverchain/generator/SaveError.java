package silverchain.generator;

import java.nio.file.Path;
import silverchain.SilverchainException;

public final class SaveError extends SilverchainException {

  SaveError(Path path) {
    super("Failed to save generated file: %s", path);
  }
}
