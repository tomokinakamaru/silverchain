package silverchain.command;

import java.io.IOException;
import silverchain.SilverchainException;

public final class InputError extends SilverchainException {

  InputError(IOException e) {
    super(e.getMessage());
  }

  InputError(String name) {
    super("File not found: %s", name);
  }
}
