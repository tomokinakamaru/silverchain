package silverchain;

import java.io.IOException;
import java.nio.file.Paths;
import silverchain.parser.ParseException;

public final class EntryPoint {

  private EntryPoint() {}

  public static void run(String... args) throws IOException, ParseException {
    main(args);
  }

  public static void main(String[] args) throws ParseException, IOException {
    Silverchain silverchain = new Silverchain();

    for (int i = 0; i < args.length; i++) {
      String arg = args[i];
      if (arg.equals("-o")) {
        silverchain.outputDirectory(Paths.get(args[i + 1]));
        i++;
      } else {
        throw new RuntimeException("Unknown argument: " + arg);
      }
    }

    silverchain.run(System.in);
  }
}
