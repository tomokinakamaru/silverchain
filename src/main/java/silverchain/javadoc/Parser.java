package silverchain.javadoc;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class Parser {

  private final JavaParser parser = new JavaParser();

  Parser() {
    CombinedTypeSolver typeSolver = new CombinedTypeSolver();
    JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
    parser.getParserConfiguration().setSymbolResolver(symbolSolver);
  }

  List<CompilationUnit> parseFiles(String path) {
    return find(path).map(this::parseFile).filter(Objects::nonNull).collect(Collectors.toList());
  }

  private CompilationUnit parseFile(Path path) {
    try {
      return parser.parse(path).getResult().orElse(null);
    } catch (IOException e) {
      return null;
    }
  }

  private Stream<Path> find(String path) {
    try {
      return Files.walk(Paths.get(path)).filter(p -> p.toString().endsWith(".java"));
    } catch (IOException e) {
      return Stream.empty();
    }
  }
}
