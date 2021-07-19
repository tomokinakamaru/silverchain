package silverchain;

import static java.util.stream.Collectors.toCollection;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import silverchain.command.WarningPrinter;
import silverchain.diagram.Diagram;
import silverchain.diagram.Diagrams;
import silverchain.generator.GeneratedFile;
import silverchain.generator.GeneratorProvider;
import silverchain.generator.JavaGenerator;
import silverchain.javadoc.Javadocs;
import silverchain.parser.Grammar;
import silverchain.parser.ParseException;
import silverchain.parser.Parser;
import silverchain.validator.JavaValidator;
import silverchain.validator.ValidatorProvider;
import silverchain.warning.WarningHandler;

public final class Silverchain {

  private Path outputDirectory = Paths.get(".");

  private GeneratorProvider generatorProvider = JavaGenerator::new;

  private ValidatorProvider validatorProvider = JavaValidator::new;

  private WarningHandler warningHandler = new WarningPrinter();

  private int maxFileCount = 0;

  public void outputDirectory(Path path) {
    outputDirectory = path;
  }

  public void generatorProvider(GeneratorProvider provider) {
    generatorProvider = provider;
  }

  public void validatorProvider(ValidatorProvider provider) {
    validatorProvider = provider;
  }

  public void warningHandler(WarningHandler handler) {
    warningHandler = handler;
  }

  public void maxFileCount(int n) {
    maxFileCount = n;
  }

  public void run(InputStream stream, String javadocPath) throws ParseException {
    List<Grammar> grammars = parse(stream);
    Diagrams diagrams = analyze(grammars);
    Javadocs javadocs = new Javadocs(javadocPath, warningHandler);
    validatorProvider.apply(diagrams).validate();

    List<GeneratedFile> files = generatorProvider.apply(diagrams, javadocs).generate();
    if (files.size() <= maxFileCount) {
      files.forEach(f -> f.save(outputDirectory));
    } else {
      throw new FileCountError(maxFileCount, files.size());
    }
  }

  private List<Grammar> parse(InputStream stream) throws ParseException {
    return new Parser(stream).start();
  }

  private Diagrams analyze(List<Grammar> grammars) {
    return grammars.stream().map(this::analyze).collect(toCollection(Diagrams::new));
  }

  private Diagram analyze(Grammar grammar) {
    grammar.validate();
    return grammar.diagram().compile();
  }
}
