package silverchain.command;

public final class UnknownOption extends RuntimeException {

  UnknownOption(String name) {
    super("Unknown option: " + name);
  }
}
