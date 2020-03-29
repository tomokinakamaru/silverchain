package silverchain.command;

import java.io.IOException;
import silverchain.parser.ParseException;

public final class EntryPoint {
  public static void main(String[] args) throws IOException, ParseException {
    System.exit(Command.run(args));
  }
}
