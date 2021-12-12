package silverchain.internal.middleware.graph.checker;

import silverchain.SilverchainException;

public class FileCountError extends SilverchainException {

  protected static final String FORMAT = "Exceeding max file count: %d";

  protected FileCountError(int count) {
    super(FORMAT, count);
  }
}
