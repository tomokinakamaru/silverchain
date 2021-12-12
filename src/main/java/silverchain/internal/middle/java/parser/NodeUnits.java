package silverchain.internal.middle.java.parser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import silverchain.internal.middle.graph.data.graph.Node;

class NodeUnits {

  private final Map<Node, CompilationUnit> map = new HashMap<>();

  CompilationUnit put(Node node, CompilationUnit unit) {
    map.put(node, unit);
    return unit;
  }

  ClassOrInterfaceDeclaration get(Node node) {
    if (!map.containsKey(node)) return null;
    return map.get(node).getTypes().stream()
        .findAny()
        .map(BodyDeclaration::asClassOrInterfaceDeclaration)
        .orElse(null);
  }

  Collection<CompilationUnit> get() {
    return map.values();
  }
}
