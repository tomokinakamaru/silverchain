package silverchain.command;

import java.io.PrintWriter;
import silverchain.warning.Warning;
import silverchain.warning.WarningHandler;

public final class WarningPrinter implements WarningHandler {

  private final PrintWriter writer;

  public WarningPrinter(PrintWriter writer) {
    this.writer = writer;
  }

  public WarningPrinter() {
    this(new PrintWriter(System.err));
  }

  @Override
  public void accept(Warning warning) {
    writer.print("WARNING: ");
    writer.println(warning);
    writer.flush();
  }
}
