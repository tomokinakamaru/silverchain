package silverchain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.IExecutionExceptionHandler;
import picocli.CommandLine.IParameterExceptionHandler;
import picocli.CommandLine.IVersionProvider;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;
import silverchain.generator.Generator;
import silverchain.validator.Validator;

@Command(
    name = "silverchain",
    versionProvider = Cli.class,
    sortOptions = false,
    optionListHeading = "%nOptions:%n",
    separator = " ",
    customSynopsis = {"silverchain [options]"})
public final class Cli
    implements Callable<Integer>,
        IVersionProvider,
        IExecutionExceptionHandler,
        IParameterExceptionHandler {

  @SuppressWarnings("unused")
  @Option(
      names = {"-h", "--help"},
      usageHelp = true,
      description = "Show this message and exit")
  private boolean helpRequested;

  @SuppressWarnings("unused")
  @Option(
      names = {"-v", "--version"},
      versionHelp = true,
      description = "Show version and exit")
  private boolean versionRequested;

  @SuppressWarnings("unused")
  @Option(
      names = {"-i", "--input"},
      description = "Input grammar file",
      defaultValue = "-",
      paramLabel = "<path>")
  private String input;

  @SuppressWarnings("unused")
  @Option(
      names = {"-o", "--output"},
      description = "Output directory",
      defaultValue = ".",
      paramLabel = "<path>")
  private String output;

  @SuppressWarnings("unused")
  @Option(
      names = {"-j", "--javadoc"},
      description = "Javadoc source directory",
      paramLabel = "<path>")
  private String javadoc;

  @SuppressWarnings("unused")
  @Option(
      names = {"-m", "--max-file-count"},
      description = "Max number of generated files",
      paramLabel = "<n>",
      defaultValue = "500")
  private int maxFileCount;

  public static void main(String[] args) {
    System.exit(run(args));
  }

  public static int run(String... args) {
    Cli cli = new Cli();
    return new CommandLine(cli)
        .setExecutionExceptionHandler(cli)
        .setParameterExceptionHandler(cli)
        .execute(args);
  }

  private Cli() {}

  @Override
  public Integer call() throws Exception {
    Silverchain silverchain = new Silverchain();
    silverchain.outputDirectory(Paths.get(output));
    silverchain.generatorProvider(Generator::new);
    silverchain.validatorProvider(Validator::new);
    silverchain.maxFileCount(maxFileCount);
    InputStream stream = open(input);
    silverchain.run(stream, javadoc);
    stream.close();
    return 0;
  }

  @Override
  public String[] getVersion() {
    return new String[] {SilverchainProperties.VERSION};
  }

  @Override
  public int handleExecutionException(Exception e, CommandLine c, ParseResult r) {
    System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
    return 1;
  }

  @Override
  public int handleParseException(ParameterException e, String[] args) {
    System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
    return 2;
  }

  private InputStream open(String name) throws FileNotFoundException {
    return name.equals("-") ? System.in : new FileInputStream(name);
  }
}
