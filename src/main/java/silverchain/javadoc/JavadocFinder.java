package silverchain.javadoc;

import static java.util.stream.Collectors.toList;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.nodeTypes.NodeWithName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class JavadocFinder {

  private final Parser parser = new Parser();

  Map<JavadocKey, JavadocComment> find(String path) {
    Map<JavadocKey, JavadocComment> map = new HashMap<>();

    Parameter parameter = new Parameter();
    parameter.setType("Object");

    for (CompilationUnit unit : parser.parseFiles(path)) {
      String pkg = unit.getPackageDeclaration().map(NodeWithName::getNameAsString).orElse(null);
      for (ClassOrInterfaceDeclaration cls : findClasses(unit)) {
        List<MethodDeclaration> methods = findPublicMethods(cls);
        for (MethodDeclaration method : methods) {
          for (int i = 0; i < method.getParameters().size(); i++) {
            Parameter p = method.getParameter(i);
            if (isTypeParameter(p.getType())) {
              method.setParameter(i, parameter);
            }
          }
        }

        List<ClassOrInterfaceType> actions = findImplementedActions(cls);
        for (ClassOrInterfaceType type : actions) {
          for (MethodDeclaration method : methods) {
            String c = type.getNameAsString();
            String m = method.getSignature().asString();
            JavadocKey key = new JavadocKey(pkg, c, m);
            map.put(key, method.getJavadocComment().orElse(null));
          }
        }
      }
    }

    return map;
  }

  private static List<ClassOrInterfaceDeclaration> findClasses(CompilationUnit unit) {
    return unit.findAll(ClassOrInterfaceDeclaration.class).stream()
        .filter(d -> !d.isInterface())
        .collect(toList());
  }

  private static List<MethodDeclaration> findPublicMethods(ClassOrInterfaceDeclaration decl) {
    return decl.findAll(MethodDeclaration.class).stream()
        .filter(d -> d.hasModifier(Modifier.Keyword.PUBLIC))
        .collect(toList());
  }

  private static boolean isTypeParameter(Type type) {
    try {
      type.resolve();
    } catch (UnsolvedSymbolException ignored) {
    } catch (UnsupportedOperationException e) {
      return true;
    }
    return false;
  }

  private static List<ClassOrInterfaceType> findImplementedActions(ClassOrInterfaceDeclaration d) {
    return d.getImplementedTypes().stream()
        .filter(JavadocFinder::isActionInterface)
        .collect(toList());
  }

  private static boolean isActionInterface(ClassOrInterfaceType type) {
    String name = type.getNameAsString();
    return name.startsWith("I") && name.endsWith("Action");
  }
}
