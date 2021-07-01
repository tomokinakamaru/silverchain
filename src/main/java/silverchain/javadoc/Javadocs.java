package silverchain.javadoc;

import static com.github.javaparser.ast.Modifier.Keyword.PUBLIC;
import static java.util.stream.Collectors.toList;

import com.github.javaparser.ast.CompilationUnit;
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
import silverchain.warning.WarningHandler;

public final class Javadocs extends HashMap<Key, JavadocComment> {

  private final String path;

  private final Parser parser = new Parser();

  private final WarningHandler warningHandler;

  private static final Parameter dummyParameter = new Parameter();

  static {
    dummyParameter.setType("Object");
  }

  public Javadocs(String path, WarningHandler warningHandler) {
    this.path = path;
    this.warningHandler = warningHandler;
  }

  public void init() {
    if (path != null) {
      load();
      if (size() == 0) {
        warningHandler.accept(new NoJavadocFound(path));
      }
    }
  }

  public JavadocComment get(String pkg, String cls, String method) {
    return get(new Key(pkg, cls, method));
  }

  private void load() {
    parser.parseFiles(path).forEach(this::load);
  }

  private void load(CompilationUnit unit) {
    String pkg = unit.getPackageDeclaration().map(NodeWithName::getNameAsString).orElse(null);
    findClasses(unit).forEach(c -> load(c, pkg));
  }

  private void load(ClassOrInterfaceDeclaration cls, String pkg) {
    Map<String, JavadocComment> comments = findComments(cls);
    List<ClassOrInterfaceType> actions = findImplementedActions(cls);
    for (ClassOrInterfaceType type : actions) {
      for (Entry<String, JavadocComment> entry : comments.entrySet()) {
        Key key = new Key(pkg, type.getNameAsString(), entry.getKey());
        put(key, entry.getValue());
      }
    }
  }

  private static List<ClassOrInterfaceDeclaration> findClasses(CompilationUnit unit) {
    return unit.findAll(ClassOrInterfaceDeclaration.class).stream()
        .filter(d -> !d.isInterface())
        .collect(toList());
  }

  private static Map<String, JavadocComment> findComments(ClassOrInterfaceDeclaration cls) {
    Map<String, JavadocComment> map = new HashMap<>();
    for (MethodDeclaration method : findPublicMethods(cls)) {
      MethodDeclaration m = method.clone();
      for (int i = 0; i < method.getParameters().size(); i++) {
        if (isTypeParameter(method.getParameter(i).getType())) {
          m.setParameter(i, dummyParameter);
        }
      }
      map.put(m.getSignature().asString(), m.getJavadocComment().orElse(null));
    }
    return map;
  }

  private static List<MethodDeclaration> findPublicMethods(ClassOrInterfaceDeclaration decl) {
    return decl.findAll(MethodDeclaration.class).stream()
        .filter(d -> d.hasModifier(PUBLIC))
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
    return d.getImplementedTypes().stream().filter(Javadocs::isActionInterface).collect(toList());
  }

  private static boolean isActionInterface(ClassOrInterfaceType type) {
    String name = type.getNameAsString();
    return name.startsWith("I") && name.endsWith("Action");
  }
}
