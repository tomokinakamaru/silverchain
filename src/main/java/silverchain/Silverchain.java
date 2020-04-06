package silverchain;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import silverchain.diagram.Diagram;
import silverchain.generator.GeneratedFile;
import silverchain.generator.Generator;
import silverchain.generator.java.JavaGenerator;
import silverchain.parser.Grammar;
import silverchain.parser.ParseException;
import silverchain.parser.Parser;

public final class Silverchain {

  private Path outputDirectory = Paths.get(".");

  private Function<List<Diagram>, Generator> generatorProvider = JavaGenerator::new;

  public void output(Path path) {
    outputDirectory = path;
  }

  public void generatorProvider(Function<List<Diagram>, Generator> function) {
    generatorProvider = function;
  }

  public void run(InputStream stream) throws ParseException {
    Parser parser = new Parser(stream);

    List<Diagram> diagrams = new ArrayList<>();
    for (Grammar grammar : parser.grammars()) {
      grammar.validate();
      Diagram diagram = grammar.diagram();
      diagram.compile();
      diagrams.add(diagram);
    }

    Generator generator = generatorProvider.apply(diagrams);
    for (GeneratedFile file : generator.generate()) {
      file.save(outputDirectory);
    }
  }
}
