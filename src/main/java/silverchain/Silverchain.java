package silverchain;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import silverchain.analyzer.Analyzer;
import silverchain.generator.GeneratedFile;
import silverchain.generator.java.JavaGenerator;
import silverchain.parser.ParseException;
import silverchain.parser.Parser;

public final class Silverchain {

  private Path outputDirectory = Paths.get("");

  public void outputDirectory(Path path) {
    outputDirectory = path;
  }

  public void run(InputStream stream) throws ParseException, IOException {
    Parser parser = new Parser(stream);
    Analyzer analyzer = new Analyzer(parser.grammars());
    JavaGenerator generator = new JavaGenerator(analyzer.analyze());
    for (GeneratedFile file : generator.generate()) {
      file.save(outputDirectory);
    }
  }
}
