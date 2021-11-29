package silverchain.javadoc;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithName;
import com.github.javaparser.ast.nodeTypes.NodeWithTypeArguments;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.JavadocBlockTag;
import com.github.javaparser.javadoc.description.JavadocDescription;
import com.github.javaparser.javadoc.description.JavadocDescriptionElement;
import com.github.javaparser.javadoc.description.JavadocInlineTag;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import silverchain.WarningHandler;
import silverchain.parser.FormalParameter;
import silverchain.parser.FormalParameters;
import silverchain.parser.Method;

public final class Javadocs {

  private final String path;

  private final WarningHandler handler;

  private final Map<String, String> comments = new HashMap<>();

  private final JavaParser parser = new JavaParser();

  // Map<PackageName, Set<TypeName>>
  private final Map<String, Set<String>> parsedTypeNames = new HashMap<>();

  // Map<CompilationUnit, Map<Name, FQName>>
  private final Map<CompilationUnit, Map<String, String>> importedNames = new HashMap<>();

  private Set<CompilationUnit> units;

  public Javadocs(String path, WarningHandler handler) {
    this.path = path;
    this.handler = handler;
  }

  public void init() {
    if (path == null) {
      return;
    }

    load();

    if (comments.size() == 0) {
      handler.accept(new NoJavadocs(path));
    }
  }

  public String get(String pkg, String cls, int state, Method method) {
    String s1 = getQualifiedName(pkg, cls);
    String s2 = "state" + state;
    String s3 = getSignature(method);

    String k1 = s1 + "." + s2 + "$" + s3;
    if (comments.containsKey(k1)) {
      return comments.get(k1);
    }

    String k2 = s1 + "." + s3;
    return comments.get(k2);
  }

  private void load() {
    initParser();
    units = parseJavaFiles();

    loadParsedTypeNames();
    loadImportedNames();
    loadComments();
  }

  private void loadParsedTypeNames() {
    for (CompilationUnit unit : units) {
      String pkg = getPackageName(unit);
      parsedTypeNames.putIfAbsent(pkg, new HashSet<>());
      for (TypeDeclaration<?> decl : unit.getTypes()) {
        parsedTypeNames.get(pkg).add(decl.getNameAsString());
      }
    }
  }

  private void loadImportedNames() {
    for (CompilationUnit unit : units) {
      importedNames.putIfAbsent(unit, new HashMap<>());
      for (ImportDeclaration d : unit.getImports()) {
        importedNames.get(unit).put(d.getName().getIdentifier(), d.getNameAsString());
      }
    }
  }

  private void loadComments() {
    units.forEach(this::loadComments);
  }

  private void loadComments(CompilationUnit unit) {
    unit.findAll(MethodDeclaration.class).forEach(this::loadComments);
  }

  private void loadComments(MethodDeclaration declaration) {
    String pkg = getPackageName(getCompilationUnit(declaration));

    ClassOrInterfaceDeclaration decl = getClassOrInterfaceDeclaration(declaration);
    if (decl == null) {
      return;
    }

    for (ClassOrInterfaceType type : decl.getImplementedTypes()) {
      if (isActionType(type)) {
        String name = type.getNameAsString();
        String key = getQualifiedName(pkg, name) + "." + getSignature(declaration);
        String val = getComment(declaration);
        if (val != null) {
          comments.put(key, val);
        }
      }
    }
  }

  private String getComment(MethodDeclaration declaration) {
    if (!declaration.hasJavaDocComment()) {
      return null;
    }

    Javadoc src = declaration.getJavadoc().orElseThrow(RuntimeException::new);

    JavadocDescription desc = new JavadocDescription();
    for (JavadocDescriptionElement elem : src.getDescription().getElements()) {
      if (elem instanceof JavadocInlineTag) {
        JavadocInlineTag t1 = (JavadocInlineTag) elem;
        String c = findFqn(t1.getContent().trim(), declaration);
        JavadocInlineTag t2 = new JavadocInlineTag(t1.getName(), t1.getType(), " " + c);
        desc.addElement(t2);
      } else {
        desc.addElement(elem);
      }
    }

    Javadoc doc = new Javadoc(desc);
    for (JavadocBlockTag tag : src.getBlockTags()) {
      doc.addBlockTag(tag);
    }

    return stream(doc.toComment().toString().split("\n"))
        .map(s -> "  " + s)
        .collect(joining("\n"))
        .trim();
  }

  private String getSignature(MethodDeclaration declaration) {
    StringBuilder builder = new StringBuilder();
    builder.append(declaration.getNameAsString());
    builder.append("(");
    List<String> types = new ArrayList<>();
    for (Parameter parameter : declaration.getParameters()) {
      Type type = parameter.getType();
      if (isTypeParameter(type)) {
        types.add("Object");
      } else {
        Type t = type.clone();
        if (t instanceof NodeWithTypeArguments) {
          ((NodeWithTypeArguments<?>) t).setTypeArguments((NodeList<Type>) null);
        }
        String name = t.asString();
        types.add(findFqn(name, declaration));
      }
    }
    builder.append(String.join(",", types));
    builder.append(")");
    return builder.toString();
  }

  private String findFqn(String name, Node node) {
    CompilationUnit unit = getCompilationUnit(node);

    String head = name.split("\\.")[0];
    String tail = name.substring(head.length());

    if (importedNames.containsKey(unit)) {
      if (importedNames.get(unit).containsKey(head)) {
        return importedNames.get(unit).get(head) + tail;
      }
    }

    String pkg = getPackageName(unit);
    if (parsedTypeNames.containsKey(pkg)) {
      if (parsedTypeNames.get(pkg).contains(head)) {
        return getQualifiedName(pkg, head) + tail;
      }
    }

    return name;
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

  private static boolean isActionType(ClassOrInterfaceType type) {
    String name = type.getNameAsString();
    return name.endsWith("Action");
  }

  private static ClassOrInterfaceDeclaration getClassOrInterfaceDeclaration(MethodDeclaration d) {
    return d.findAncestor(ClassOrInterfaceDeclaration.class).orElse(null);
  }

  private static CompilationUnit getCompilationUnit(Node node) {
    return node.findCompilationUnit().orElseThrow(RuntimeException::new);
  }

  private static String getPackageName(CompilationUnit unit) {
    return unit.getPackageDeclaration().map(NodeWithName::getNameAsString).orElse(null);
  }

  private static String getSignature(Method method) {
    StringBuilder builder = new StringBuilder();
    builder.append(method.name());
    builder.append("(");
    if (method.parameters().formalParameters().isPresent()) {
      FormalParameters parameters = method.parameters().formalParameters().get();
      List<String> types = new ArrayList<>();
      for (FormalParameter p : parameters) {
        if (p.type().referent() == null) {
          types.add(p.type().name().toString());
        } else {
          types.add("Object");
        }
      }
      builder.append(String.join(",", types));
    }
    builder.append(")");
    return builder.toString();
  }

  private static String getQualifiedName(String name1, String name2) {
    return name1 == null ? name2 : (name1 + "." + name2);
  }

  private void initParser() {
    CombinedTypeSolver typeSolver = new CombinedTypeSolver();
    JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
    parser.getParserConfiguration().setSymbolResolver(symbolSolver);
  }

  private Set<CompilationUnit> parseJavaFiles() {
    return findJavaFiles()
        .map(this::parseJavaFile)
        .filter(Objects::nonNull)
        .collect(Collectors.toSet());
  }

  private CompilationUnit parseJavaFile(Path path) {
    try {
      return parser.parse(path).getResult().orElse(null);
    } catch (IOException e) {
      return null;
    }
  }

  private Stream<Path> findJavaFiles() {
    try {
      return Files.walk(Paths.get(path)).filter(p -> p.toString().endsWith(".java"));
    } catch (IOException e) {
      return Stream.empty();
    }
  }
}
