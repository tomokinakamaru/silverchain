package silverchain;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import silverchain.generator.GeneratedFile;
import silverchain.generator.java.JavaGenerator;
import silverchain.grammar.Grammar;
import silverchain.graph.GraphNode;
import silverchain.parser.ParseException;
import silverchain.parser.Parser;

public final class Silverchain {

  private Path outputDirectory = Paths.get(".");

  public void output(Path path) {
    outputDirectory = path;
  }

  public void run(InputStream stream) throws ParseException, IOException {
    Parser parser = new Parser(stream);

    List<List<GraphNode>> list = new ArrayList<>();
    for (Grammar grammar : parser.grammars()) {
      grammar.resolveReferences(new HashSet<>(grammar.typeParameters()));
      list.add(grammar.graph().compile());
    }

    for (List<GraphNode> nodes : list) {
      JavaGenerator generator = new JavaGenerator(nodes);
      for (GeneratedFile file : generator.generate()) {
        file.save(outputDirectory);
      }
    }
  }
}
