package silverchain.codegen.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import silverchain.grammar.QualifiedName;
import silverchain.grammar.Type;
import silverchain.grammar.TypeArgument;
import silverchain.grammar.TypeArguments;
import silverchain.grammar.TypeReference;
import silverchain.graph.GraphEdge;
import silverchain.graph.GraphNode;

final class GraphAnalyzer {

  private final List<GraphNode> nodes;

  private final Type type;

  private final Set<String> parameterNames;

  private final Map<GraphNode, TypeReference> typeReferences;

  GraphAnalyzer(List<GraphNode> nodes) {
    this.nodes = nodes(nodes);
    this.type = type(nodes);
    this.parameterNames = parameterNames(nodes);
    this.typeReferences = typeReferences(nodes);
  }

  List<GraphNode> nodes() {
    return nodes;
  }

  String name(GraphNode node) {
    return type.name().name();
  }

  QualifiedName packageName(GraphNode node) {
    int n = nodes.indexOf(node);
    QualifiedName qualifier = type.name().qualifier();
    return n == 0 ? qualifier : new QualifiedName(qualifier, "state" + n);
  }

  QualifiedName qualifiedName(GraphNode node) {
    return new QualifiedName(packageName(node), name(node));
  }

  TypeReference destination(GraphEdge edge) {
    if (nodes.contains(edge.destination())) {
      QualifiedName qualifiedName = qualifiedName(edge.destination());
      TypeArguments typeArguments = typeArguments(edge.destination().tags());
      return new TypeReference(qualifiedName, typeArguments);
    }
    return typeReferences.get(edge.destination());
  }

  List<String> tags(GraphEdge edge) {
    List<String> list = parameters(edge.destination());
    list.removeAll(parameters(edge.source()));
    return list;
  }

  private List<String> parameters(GraphNode node) {
    TypeReference reference = typeReferences.get(node);
    return reference == null ? node.tags() : new ArrayList<>(findTypeParameters(reference));
  }

  private Set<String> findTypeParameters(TypeReference reference) {
    if (reference == null) {
      return Collections.emptySet();
    }
    Set<String> set = new LinkedHashSet<>();
    QualifiedName name = reference.name();
    if (parameterNames.contains(name.name()) && name.qualifier() == null) {
      set.add(name.name());
    }
    set.addAll(findTypeParameters(reference.arguments()));
    return set;
  }

  private Set<String> findTypeParameters(TypeArguments arguments) {
    if (arguments == null) {
      return Collections.emptySet();
    }
    Set<String> set = findTypeParameters(arguments.head());
    if (arguments.tail() != null) {
      set.addAll(findTypeParameters(arguments.tail()));
    }
    return set;
  }

  private Set<String> findTypeParameters(TypeArgument argument) {
    return findTypeParameters(argument.reference());
  }

  private static List<GraphNode> nodes(List<GraphNode> nodes) {
    return nodes
        .stream()
        .filter(n -> !n.isStart())
        .filter(n -> !n.isEnd())
        .filter(n -> n.edges().stream().noneMatch(e -> e.label().is(TypeReference.class)))
        .collect(Collectors.toList());
  }

  private static TypeArguments typeArguments(List<String> names) {
    TypeArguments args = null;
    for (int i = names.size() - 1; i >= 0; i--) {
      args = new TypeArguments(typeArgument(names.get(i)), args);
    }
    return args;
  }

  private static TypeArgument typeArgument(String name) {
    QualifiedName qualifiedName = new QualifiedName(null, name);
    TypeReference reference = new TypeReference(qualifiedName, null);
    return new TypeArgument(reference);
  }

  private static Type type(List<GraphNode> nodes) {
    return nodes.get(0).edges().get(0).label().as(Type.class);
  }

  private static Set<String> parameterNames(List<GraphNode> nodes) {
    return nodes.stream().flatMap(n -> n.tags().stream()).collect(Collectors.toSet());
  }

  private static Map<GraphNode, TypeReference> typeReferences(List<GraphNode> nodes) {
    return nodes
        .stream()
        .filter(GraphAnalyzer::hasTypeReference)
        .collect(Collectors.toMap(Function.identity(), GraphAnalyzer::typeReference));
  }

  private static boolean hasTypeReference(GraphNode node) {
    return node.edges().stream().anyMatch(e -> e.label().is(TypeReference.class));
  }

  private static TypeReference typeReference(GraphNode node) {
    return node.edges()
        .stream()
        .findFirst()
        .orElseThrow(RuntimeException::new)
        .label()
        .as(TypeReference.class);
  }
}
