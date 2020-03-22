package silverchain.codegen.java;

import static silverchain.codegen.java.ASTEncoder.encode;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import silverchain.grammar.Method;
import silverchain.grammar.TypeArguments;
import silverchain.grammar.TypeReference;
import silverchain.graph.GraphNode;

final class GraphNodeAdapter {

  private final GraphAdapter graph;

  private final GraphNode node;

  GraphNodeAdapter(GraphAdapter graph, GraphNode node) {
    this.graph = graph;
    this.node = node;
  }

  String name() {
    return graph.typeName();
  }

  String packageName() {
    int number = graph.indexOf(node);
    String qualifier = graph.typePackageName();
    if (qualifier == null) {
      return number == 0 ? null : "state" + number;
    }
    return number == 0 ? qualifier : qualifier + ".state" + number;
  }

  String qualifiedName() {
    String qualifier = packageName();
    return qualifier == null ? name() : qualifier + "." + name();
  }

  Path path() {
    return Paths.get(qualifiedName().replaceAll("\\.", File.separator) + ".java");
  }

  List<String> tags() {
    Set<String> set = new LinkedHashSet<>(node.tags());
    set.addAll(findTypeParameters());
    return new ArrayList<>(set);
  }

  List<String> typeReferences() {
    return node.edges()
        .stream()
        .filter(e -> e.label().is(TypeReference.class))
        .map(e -> e.label().as(TypeReference.class))
        .map(ASTEncoder::encode)
        .collect(Collectors.toList());
  }

  List<GraphEdgeAdapter> edges() {
    return node.edges()
        .stream()
        .filter(e -> e.label().is(Method.class))
        .map(e -> new GraphEdgeAdapter(graph, e))
        .collect(Collectors.toList());
  }

  private Set<String> findTypeParameters() {
    return node.edges()
        .stream()
        .filter(e -> e.label().is(TypeReference.class))
        .map(e -> e.label().as(TypeReference.class))
        .map(this::findTypeParameters)
        .flatMap(Collection::stream)
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  private Set<String> findTypeParameters(TypeReference reference) {
    Set<String> set = new LinkedHashSet<>();
    String name = encode(reference.name());
    if (graph.isParameter(name)) {
      set.add(name);
    }
    set.addAll(findTypeParameters(reference.arguments()));
    return set;
  }

  private Set<String> findTypeParameters(TypeArguments arguments) {
    Set<String> set = new LinkedHashSet<>();
    while (arguments != null) {
      set.addAll(findTypeParameters(arguments.head().reference()));
      arguments = arguments.tail();
    }
    return set;
  }
}
