package silverchain.command;

import silverchain.SilverchainException;

public final class UnknownOption extends SilverchainException {

  UnknownOption(String name) {
    super("Unknown option: %s", name);
  }
}
