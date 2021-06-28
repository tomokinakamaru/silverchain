package silverchain.javadoc;

import static java.nio.file.Files.walk;
import static java.nio.file.Paths.get;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

final class Parser {

  private final JavaParser parser = new JavaParser();

  Parser() {
    CombinedTypeSolver typeSolver = new CombinedTypeSolver();
    JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
    parser.getParserConfiguration().setSymbolResolver(symbolSolver);
  }

  Stream<CompilationUnit> parseFiles(String path) {
    return findJavaFiles(path).map(this::parseFile).filter(Objects::nonNull);
  }

  private CompilationUnit parseFile(Path path) {
    try {
      return parser.parse(path).getResult().orElse(null);
    } catch (IOException e) {
      return null;
    }
  }

  private Stream<Path> findJavaFiles(String path) {
    try {
      return walk(get(path)).filter(p -> p.toString().endsWith(".java"));
    } catch (IOException e) {
      return Stream.empty();
    }
  }
}
