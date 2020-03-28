package silverchain.command;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import silverchain.Silverchain;
import silverchain.parser.ParseException;

public final class EntryPoint {

  private static final ArgumentParser parser = new ArgumentParser();

  private final Arguments arguments;

  static {
    parser.add(new Option("h", "help", "show this message and exit"));
    parser.add(new Option("v", "version", "show version and exit"));
    parser.add(new Option("i", "input", "<path>", "input grammar file", "-"));
    parser.add(new Option("o", "output", "<path>", "output directory", "."));
  }

  private EntryPoint(String[] args) {
    this.arguments = new Arguments(args);
  }

  public static int run(String... args) throws IOException, ParseException {
    return new EntryPoint(args).run();
  }

  public static void main(String[] args) throws IOException, ParseException {
    System.exit(new EntryPoint(args).run());
  }

  private int run() throws IOException, ParseException {
    ParseResult result = parse();

    if (!result.success()) {
      System.err.println("error: unknown option " + result.unknownArgument());
      System.err.println(parser.help());
      return 1;
    }

    if (Boolean.parseBoolean(result.get("help"))) {
      System.out.println(parser.help());
      return 0;
    }

    if (Boolean.parseBoolean(result.get("version"))) {
      System.out.println("0.1.0");
      return 0;
    }

    Silverchain silverchain = new Silverchain();
    silverchain.outputDirectory(Paths.get(result.get("output")));
    String input = result.get("input");
    if (input.equals("-")) {
      silverchain.run(System.in);
    } else {
      try (InputStream stream = new FileInputStream(input)) {
        silverchain.run(stream);
      }
    }
    return 0;
  }

  private ParseResult parse() {
    return parser.parse(arguments);
  }
}
