package silverchain.javadoc;

import static com.github.javaparser.ast.Modifier.Keyword.PUBLIC;
import static java.util.stream.Collectors.toList;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.nodeTypes.NodeWithName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Javadocs extends HashMap<Key, Comment> {

  private final String path;

  private final Parser parser = new Parser();

  private static final Parameter dummyParameter = new Parameter();

  static {
    dummyParameter.setType("Object");
  }

  public Javadocs(String path) {
    this.path = path;
  }

  public void init() {
    if (path != null) {
      load();
      if (size() == 0) {
        System.err.println("WARNING: No javadoc comments were found");
      }
    }
  }

  public Comment get(String pkg, String cls, String method) {
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
    Map<String, Comment> comments = findComments(cls);
    List<ClassOrInterfaceType> actions = findImplementedActions(cls);
    for (ClassOrInterfaceType type : actions) {
      for (Entry<String, Comment> entry : comments.entrySet()) {
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

  private static Map<String, Comment> findComments(ClassOrInterfaceDeclaration cls) {
    Map<String, Comment> map = new HashMap<>();
    for (MethodDeclaration method : findPublicMethods(cls)) {
      MethodDeclaration m = method.clone();
      for (int i = 0; i < method.getParameters().size(); i++) {
        if (isTypeParameter(method.getParameter(i).getType())) {
          m.setParameter(i, dummyParameter);
        }
      }
      map.put(m.getSignature().asString(), m.getComment().orElse(null));
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