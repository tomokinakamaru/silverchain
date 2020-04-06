package silverchain.command;

import java.io.IOException;

public final class InputError extends RuntimeException {

  InputError(IOException e) {
    super(e.getMessage());
  }

  InputError(String name) {
    super("File not found: " + name);
  }
}
