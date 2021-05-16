package silverchain;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import silverchain.diagram.Diagram;
import silverchain.generator.Generator;
import silverchain.generator.JavaGenerator;
import silverchain.parser.Grammar;
import silverchain.parser.ParseException;
import silverchain.parser.Parser;
import silverchain.validator.JavaValidator;
import silverchain.validator.Validator;

public final class Silverchain {

  private Path outputDirectory = Paths.get(".");

  private Function<List<Diagram>, Generator> generatorProvider = JavaGenerator::new;

  private Function<List<Diagram>, Validator> validatorProvider = JavaValidator::new;

  public void outputDirectory(Path path) {
    outputDirectory = path;
  }

  public void generatorProvider(Function<List<Diagram>, Generator> provider) {
    generatorProvider = provider;
  }

  public void validatorProvider(Function<List<Diagram>, Validator> provider) {
    validatorProvider = provider;
  }

  public void run(InputStream stream) throws ParseException {
    List<Grammar> grammars = parse(stream);
    List<Diagram> diagrams = analyze(grammars);
    validatorProvider.apply(diagrams).validate();
    generatorProvider.apply(diagrams).generate().forEach(f -> f.save(outputDirectory));
  }

  private List<Grammar> parse(InputStream stream) throws ParseException {
    return new Parser(stream).start();
  }

  private List<Diagram> analyze(List<Grammar> grammars) {
    return grammars.stream().map(this::analyze).collect(Collectors.toList());
  }

  private Diagram analyze(Grammar grammar) {
    grammar.validate();
    return grammar.diagram().compile();
  }
}
