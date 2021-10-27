package silverchain;

import static java.util.stream.Collectors.toCollection;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import silverchain.command.WarningPrinter;
import silverchain.diagram.Diagram;
import silverchain.diagram.Diagrams;
import silverchain.generator.GeneratedFile;
import silverchain.generator.GeneratorProvider;
import silverchain.generator.JavaGenerator;
import silverchain.javadoc.Javadocs;
import silverchain.parser.Grammar;
import silverchain.parser.Input;
import silverchain.parser.ParseException;
import silverchain.parser.Parser;
import silverchain.parser.QualifiedName;
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
    Input input = parse(stream);
    Diagrams diagrams = analyze(input);
    Javadocs javadocs = new Javadocs(javadocPath, warningHandler);
    validatorProvider.apply(diagrams).validate();

    List<GeneratedFile> files = generatorProvider.apply(diagrams, javadocs).generate();
    if (files.size() <= maxFileCount) {
      files.forEach(f -> f.save(outputDirectory));
    } else {
      throw new FileCountError(maxFileCount, files.size());
    }
  }

  private Input parse(InputStream stream) throws ParseException {
    return new Parser(stream).start();
  }

  private Diagrams analyze(Input input) {
    Map<String, QualifiedName> importMap = input.importMap();
    return input.grammars().stream()
        .map(g -> analyze(g, importMap))
        .collect(toCollection(Diagrams::new));
  }

  private Diagram analyze(Grammar grammar, Map<String, QualifiedName> importMap) {
    grammar.validate();
    return grammar.diagram(importMap).compile();
  }
}
