package silverchain.command;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.RecognitionException;
import silverchain.FileCountError;
import silverchain.Silverchain;
import silverchain.SilverchainException;
import silverchain.SilverchainProperties;
import silverchain.generator.JavaGenerator;
import silverchain.generator.SaveError;
import silverchain.parser.DuplicateDeclaration;
import silverchain.parser.adapter.ParseError;
import silverchain.parser.adapter.TokenizeError;
import silverchain.validator.JavaValidator;
import silverchain.validator.ValidationError;

public final class Command {

  private static final ArgumentParser parser = new ArgumentParser();

  private static final Map<Class<? extends Throwable>, Integer> errorCodes = new HashMap<>();

  private final PrintStream stdout;

  private final PrintStream stderr;

  private final Arguments arguments;

  static {
    parser.add(new Option("h", "help", "Show this message and exit"));
    parser.add(new Option("v", "version", "Show version and exit"));
    parser.add(new Option("i", "input", "<path>", "Input grammar file", "-"));
    parser.add(new Option("o", "output", "<path>", "Output directory", "."));
    parser.add(new Option("j", "javadoc", "<path>", "Javadoc source directory", null));
    parser.add(new Option("m", "max-file-count", "<n>", "Max number of generated files", "500"));
  }

  static {
    errorCodes.put(UnknownOption.class, 101);
    errorCodes.put(InputError.class, 103);
    errorCodes.put(TokenizeError.class, 104);
    errorCodes.put(ParseError.class, 105);
    errorCodes.put(DuplicateDeclaration.class, 106);
    errorCodes.put(ValidationError.class, 107);
    errorCodes.put(SaveError.class, 108);
    errorCodes.put(FileCountError.class, 109);
  }

  private Command(PrintStream stdout, PrintStream stderr, String[] args) {
    this.stdout = stdout;
    this.stderr = stderr;
    this.arguments = new Arguments(args);
  }

  public static int run(PrintStream stdout, PrintStream stderr, String... args) {
    try {
      new Command(stdout, stderr, args).run();
    } catch (SilverchainException e) {
      stderr.println(e.getMessage());
      return errorCodes.get(e.getClass());
    }
    return 0;
  }

  private void run() throws RecognitionException {
    ParseResult result = parser.parse(arguments);
    if (!result.success()) {
      throw new UnknownOption(result.unknownOption());
    } else if (result.getFlag("help")) {
      stdout.println(parser.help());
    } else if (result.getFlag("version")) {
      stdout.println(SilverchainProperties.VERSION);
    } else {
      run(result);
    }
  }

  private void run(ParseResult result) throws RecognitionException {
    Silverchain silverchain = new Silverchain();
    silverchain.outputDirectory(Paths.get(result.get("output")));
    silverchain.generatorProvider(JavaGenerator::new);
    silverchain.validatorProvider(JavaValidator::new);
    silverchain.warningHandler(new WarningPrinter(stderr));
    silverchain.maxFileCount(Integer.parseInt(result.get("max-file-count")));
    try (InputStream stream = open(result.get("input"))) {
      silverchain.run(stream, result.get("javadoc"));
    } catch (IOException e) {
      throw new InputError(e);
    }
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
}
