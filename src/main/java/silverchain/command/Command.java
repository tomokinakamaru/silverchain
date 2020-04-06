package silverchain.command;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import silverchain.Silverchain;
import silverchain.diagram.Diagram;
import silverchain.generator.EncodeError;
import silverchain.generator.Generator;
import silverchain.generator.SaveError;
import silverchain.generator.java.JavaGenerator;
import silverchain.parser.DuplicateDeclaration;
import silverchain.parser.ParseException;
import silverchain.parser.TokenMgrError;

public final class Command {

  private static final ArgumentParser parser = new ArgumentParser();

  private static final Map<Class<? extends Throwable>, Integer> errorCodes = new HashMap<>();

  private static final String VERSION = "0.1.0";

  private final Arguments arguments;

  static {
    parser.add(new Option("h", "help", "Show this message and exit"));
    parser.add(new Option("v", "version", "Show version and exit"));
    parser.add(new Option("i", "input", "<path>", "Input grammar file", "-"));
    parser.add(new Option("o", "output", "<path>", "Output directory", "."));
    parser.add(new Option("l", "language", "<lang>", "Output language", "java"));
  }

  static {
    errorCodes.put(UnknownOption.class, 101);
    errorCodes.put(UnsupportedLanguage.class, 102);
    errorCodes.put(InputError.class, 103);
    errorCodes.put(TokenMgrError.class, 104);
    errorCodes.put(ParseException.class, 105);
    errorCodes.put(DuplicateDeclaration.class, 106);
    errorCodes.put(EncodeError.class, 107);
    errorCodes.put(SaveError.class, 108);
  }

  private Command(String[] args) {
    this.arguments = new Arguments(args);
  }

  public static int run(String... args) {
    try {
      new Command(args).run();
    } catch (UnknownOption
        | UnsupportedLanguage
        | InputError
        | TokenMgrError
        | ParseException
        | DuplicateDeclaration
        | EncodeError
        | SaveError e) {
      writeError(e.getMessage());
      return errorCodes.get(e.getClass());
    }
    return 0;
  }

  private void run() throws ParseException {
    ParseResult result = parser.parse(arguments);
    if (!result.success()) {
      throw new UnknownOption(result.unknownOption());
    } else if (result.getFlag("help")) {
      write(parser.help());
    } else if (result.getFlag("version")) {
      write(VERSION);
    } else {
      run(result);
    }
  }

  private void run(ParseResult result) throws ParseException {
    Silverchain silverchain = new Silverchain();
    silverchain.outputDirectory(Paths.get(result.get("output")));
    silverchain.generatorProvider(generatorProvider(result.get("language")));
    try (InputStream stream = open(result.get("input"))) {
      silverchain.run(stream);
    } catch (IOException e) {
      throw new InputError(e);
    }
  }

  private Function<List<Diagram>, Generator> generatorProvider(String language) {
    if (language.equals("java")) {
      return JavaGenerator::new;
    }
    throw new UnsupportedLanguage(language);
  }

  private InputStream open(String name) {
    if (name.equals("-")) {
      return System.in;
    }
    try {
      return new FileInputStream(name);
    } catch (FileNotFoundException e) {
      throw new InputError(name);
    }
  }

  private static void write(String s) {
    System.out.println(s);
  }

  private static void writeError(String s) {
    System.err.println(s);
  }
}
