package silverchain;

import static java.util.stream.Collectors.toCollection;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.RecognitionException;
import silverchain.command.WarningPrinter;
import silverchain.diagram.Diagram;
import silverchain.diagram.Diagrams;
import silverchain.generator.GeneratedFile;
import silverchain.generator.GeneratorProvider;
import silverchain.generator.JavaGenerator;
import silverchain.javadoc.Javadocs;
import silverchain.parser.*;
import silverchain.parser.adapter.Parser;
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

  public void run(InputStream stream, String javadocPath) throws RecognitionException, IOException {
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

  private Input parse(InputStream stream) throws RecognitionException, IOException {
    return (Input) new Parser(stream).parse(AgParser::input);
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
