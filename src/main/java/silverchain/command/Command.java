package silverchain.command;

import java.io.*;
import java.nio.file.Paths;
import picocli.CommandLine;
import picocli.CommandLine.*;
import silverchain.Silverchain;
import silverchain.SilverchainProperties;
import silverchain.generator.Generator;
import silverchain.validator.Validator;

@CommandLine.Command(
    name = "silverchain",
    versionProvider = Command.class,
    sortOptions = false,
    optionListHeading = "%nOptions:%n",
    separator = " ",
    customSynopsis = {"silverchain [options]"})
public final class Command
    implements Runnable, IVersionProvider, IExecutionExceptionHandler, IParameterExceptionHandler {

  @SuppressWarnings("unused")
  @CommandLine.Option(
      names = {"-h", "--help"},
      usageHelp = true,
      description = "Show this message and exit")
  private boolean helpRequested;

  @SuppressWarnings("unused")
  @CommandLine.Option(
      names = {"-v", "--version"},
      versionHelp = true,
      description = "Show version and exit")
  private boolean versionRequested;

  @SuppressWarnings("unused")
  @CommandLine.Option(
      names = {"-i", "--input"},
      description = "Input grammar file",
      defaultValue = "-",
      paramLabel = "<path>")
  private String input;

  @SuppressWarnings("unused")
  @CommandLine.Option(
      names = {"-o", "--output"},
      description = "Output directory",
      defaultValue = ".",
      paramLabel = "<path>")
  private String output;

  @SuppressWarnings("unused")
  @CommandLine.Option(
      names = {"-j", "--javadoc"},
      description = "Javadoc source directory",
      paramLabel = "<path>")
  private String javadoc;

  @SuppressWarnings("unused")
  @CommandLine.Option(
      names = {"-m", "--max-file-count"},
      description = "Max number of generated files",
      paramLabel = "<n>",
      defaultValue = "500")
  private int maxFileCount;

  public static void main(String[] args) {
    System.exit(run(args));
  }

  public static int run(String... args) {
    Command command = new Command();
    return new CommandLine(command)
        .setExecutionExceptionHandler(command)
        .setParameterExceptionHandler(command)
        .execute(args);
  }

  @Override
  public void run() {
    Silverchain silverchain = new Silverchain();
    silverchain.outputDirectory(Paths.get(output));
    silverchain.generatorProvider(Generator::new);
    silverchain.validatorProvider(Validator::new);
    silverchain.maxFileCount(maxFileCount);
    try (InputStream stream = open(input)) {
      silverchain.run(stream, javadoc);
    } catch (IOException e) {
      throw new InputError(e);
    }
  }

  @Override
  public String[] getVersion() {
    return new String[] {SilverchainProperties.VERSION};
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

  @Override
  public int handleExecutionException(Exception e, CommandLine c, ParseResult r) {
    System.err.println(e.getMessage());
    return 1;
  }

  @Override
  public int handleParseException(ParameterException ex, String[] args) {
    System.err.println(ex.getMessage());
    return 2;
  }
}
