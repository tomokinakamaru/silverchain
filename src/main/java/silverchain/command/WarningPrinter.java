package silverchain.command;

import java.io.PrintStream;
import silverchain.warning.Warning;
import silverchain.warning.WarningHandler;

public final class WarningPrinter implements WarningHandler {

  private final PrintStream stream;

  public WarningPrinter(PrintStream stream) {
    this.stream = stream;
  }

  public WarningPrinter() {
    this(System.err);
  }

  @Override
  public void accept(Warning warning) {
    stream.print("WARNING: ");
    stream.println(warning);
  }
}
