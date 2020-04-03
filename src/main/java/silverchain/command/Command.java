package silverchain.command;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import silverchain.Silverchain;
import silverchain.parser.ParseException;

public final class Command {

  private static final ArgumentParser parser = new ArgumentParser("silverchain");

  private final Arguments arguments;

  static {
    parser.add(new Option("h", "help", "show this message and exit"));
    parser.add(new Option("v", "version", "show version and exit"));
    parser.add(new Option("i", "input", "<path>", "input grammar file", "-"));
    parser.add(new Option("o", "output", "<path>", "output directory", "."));
  }

  private Command(String[] args) {
    this.arguments = new Arguments(args);
  }

  public static int run(String... args) throws IOException, ParseException {
    return new Command(args).run();
  }

  private int run() throws IOException, ParseException {
    ParseResult result = parser.parse(arguments);
    if (!result.success()) {
      return printError(result.unknownOption());
    }
    if (result.getFlag("help")) {
      return printHelp();
    }
    if (result.getFlag("version")) {
      return printVersion();
    }
    return run(result);
  }

  private int run(ParseResult result) throws IOException, ParseException {
    Silverchain silverchain = new Silverchain();
    silverchain.output(Paths.get(result.get("output")));
    try (InputStream stream = open(result.get("input"))) {
      silverchain.run(stream);
    }
    return 0;
  }

  private InputStream open(String name) throws FileNotFoundException {
    if (name.equals("-")) {
      return System.in;
    }
    return new FileInputStream(name);
  }

  private int printError(String unknownOption) {
    writeError("error: unknown option " + unknownOption);
    writeError(parser.help());
    return 1;
  }

  private int printHelp() {
    write(parser.help());
    return 0;
  }

  private int printVersion() {
    write("0.1.0");
    return 0;
  }

  private void write(String s) {
    System.out.println(s);
  }

  private void writeError(String s) {
    System.err.println(s);
  }
}
