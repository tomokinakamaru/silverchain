package impl.convention;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import impl.Convention;
import impl.Violation;
import impl.Violations;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ApiAnnotation implements Convention {

  private static final List<String> MAINTAINED = new ArrayList<>();

  static {
    MAINTAINED.add("Silverchain");
    MAINTAINED.add("SilverchainException");
    MAINTAINED.add("SilverchainWarning");
  }

  @Override
  public Violations check(String path, CompilationUnit unit) {
    return unit.getTypes().stream()
        .map(t -> check(path, t))
        .filter(Objects::nonNull)
        .collect(Collectors.toCollection(Violations::new));
  }

  private static Violation check(String path, TypeDeclaration<?> decl) {
    if (path.contains("/test/")) return null;
    AnnotationExpr expr = decl.getAnnotationByName("API").orElse(null);
    return expr == null ? new Violation(path, "No @API(...)") : check(path, decl, expr);
  }

  private static Violation check(String path, TypeDeclaration<?> decl, AnnotationExpr expr) {
    String name = decl.getName().asString();
    return MAINTAINED.contains(name) ? checkMaintained(path, expr) : checkInternal(path, expr);
  }

  private static Violation checkInternal(String path, AnnotationExpr expr) {
    if (expr.toString().equals("@API(status = API.Status.INTERNAL)")) return null;
    return new Violation(path, "Wrong @API(...)");
  }

  private static Violation checkMaintained(String path, AnnotationExpr expr) {
    if (expr.toString().equals("@API(status = API.Status.MAINTAINED)")) return null;
    return new Violation(path, "Wrong @API(...)");
  }
}
