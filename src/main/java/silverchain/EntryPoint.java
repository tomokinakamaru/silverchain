package silverchain;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import silverchain.parser.ParseException;

public final class EntryPoint {

  private EntryPoint() {}

  public static void run(String... args) throws IOException, ParseException {
    main(args);
  }

  public static void main(String[] args) throws ParseException, IOException {
    boolean printHelp = false;
    boolean printVersion = false;
    InputStream inputStream = System.in;
    Path outputDirectory = Paths.get(".");

    for (int i = 0; i < args.length; i++) {
      String arg = args[i];
      switch (arg) {
        case "-h":
        case "--help":
          printHelp = true;
          break;

        case "-v":
        case "--version":
          printVersion = true;
          break;

        case "-i":
          inputStream = new FileInputStream(Paths.get(args[i + 1]).toString());
          i++;
          break;

        case "-o":
          outputDirectory = Paths.get(args[i + 1]);
          i++;
          break;

        default:
          throw new RuntimeException("Unknown argument: " + arg);
      }
    }

    if (printHelp) {
      printHelp();
    } else if (printVersion) {
      printVersion();
    } else {
      Silverchain silverchain = new Silverchain();
      silverchain.outputDirectory(outputDirectory);
      silverchain.run(inputStream);
    }
  }

  private static void printHelp() {
    System.out.println("usage: silverchain [-h] [-v] [-i <path>] [-o <path>]");
    System.out.println();
    System.out.println("optional arguments:");
    System.out.println("  -h, --help     show this help message and exit");
    System.out.println("  -v, --version  show version and exit");
    System.out.println("  -i <path>      specify input file");
    System.out.println("  -o <path>      specify output directory");
  }

  private static void printVersion() {
    System.out.println("0.1.0");
  }
}
