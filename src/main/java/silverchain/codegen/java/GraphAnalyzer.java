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
import silverchain.grammar.TypeParameter;
import silverchain.grammar.TypeParameterList;
import silverchain.grammar.TypeReference;
import silverchain.graph.GraphEdge;
import silverchain.graph.GraphNode;
import silverchain.graph.GraphTag;

final class GraphAnalyzer {

  private final List<GraphNode> nodes;

  private final Type type;

  private final List<TypeParameter> definedParameters;

  private final Map<GraphNode, TypeReference> typeReferences;

  GraphAnalyzer(List<GraphNode> nodes) {
    this.nodes = nodes(nodes);
    this.type = type(nodes);
    this.definedParameters = parameterNames(nodes);
    this.typeReferences = typeReferences(nodes);
  }

  List<GraphNode> nodes() {
    return nodes;
  }

  List<TypeParameter> parameters() {
    return definedParameters;
  }

  QualifiedName packageName() {
    return type.name().qualifier();
  }

  String name() {
    return type.name().name();
  }

  int indexOf(GraphNode node) {
    return nodes.indexOf(node);
  }

  String implModifier(GraphNode node) {
    int n = nodes.indexOf(node);
    return n == 0 ? "public " : "";
  }

  String implName(GraphNode node) {
    int n = nodes.indexOf(node);
    return n == 0 ? type.name().name() : "State" + n;
  }

  QualifiedName implQualifiedName(GraphNode node) {
    return new QualifiedName(packageName(), implName(node));
  }

  String apiModifier(GraphNode node) {
    int n = nodes.indexOf(node);
    return n == 0 ? "" : "public ";
  }

  String apiName(GraphNode node) {
    int n = nodes.indexOf(node);
    return n == 0 ? "I" + type.name().name() : type.name().name();
  }

  QualifiedName apiPackageName(GraphNode node) {
    int n = nodes.indexOf(node);
    QualifiedName qualifier = packageName();
    return n == 0 ? qualifier : new QualifiedName(qualifier, "state" + n);
  }

  QualifiedName apiQualifiedName(GraphNode node) {
    return new QualifiedName(apiPackageName(node), apiName(node));
  }

  TypeReference listenerDestination(GraphEdge edge) {
    if (nodes.contains(edge.destination())) {
      return null;
    }
    return typeReferences.get(edge.destination());
  }

  TypeReference destination(GraphEdge edge) {
    if (nodes.contains(edge.destination())) {
      QualifiedName qualifiedName = apiQualifiedName(edge.destination());
      TypeArguments typeArguments = typeArguments(edge.destination().tags());
      return new TypeReference(qualifiedName, typeArguments);
    }
    return typeReferences.get(edge.destination());
  }

  List<TypeParameter> tags(GraphNode node) {
    return node.tags()
        .stream()
        .map(t -> t.as(TypeParameter.class))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  List<TypeParameter> tags(GraphEdge edge) {
    List<TypeParameter> list = parameters(edge.destination());
    list.removeAll(parameters(edge.source()));
    return list;
  }

  private List<TypeParameter> parameters(GraphNode node) {
    TypeReference reference = typeReferences.get(node);
    if (reference == null) {
      return node.tags().stream().map(t -> t.as(TypeParameter.class)).collect(Collectors.toList());
    }
    return new ArrayList<>(findTypeParameters(reference));
  }

  private Set<TypeParameter> findTypeParameters(TypeReference reference) {
    if (reference == null) {
      return Collections.emptySet();
    }
    Set<TypeParameter> set = new LinkedHashSet<>();
    QualifiedName name = reference.name();
    TypeParameter p = parameter(reference);
    if (p != null) {
      set.add(p);
    }
    set.addAll(findTypeParameters(reference.arguments()));
    return set;
  }

  private Set<TypeParameter> findTypeParameters(TypeArguments arguments) {
    if (arguments == null) {
      return Collections.emptySet();
    }
    Set<TypeParameter> set = findTypeParameters(arguments.head());
    if (arguments.tail() != null) {
      set.addAll(findTypeParameters(arguments.tail()));
    }
    return set;
  }

  private Set<TypeParameter> findTypeParameters(TypeArgument argument) {
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

  private static TypeArguments typeArguments(List<GraphTag> names) {
    TypeArguments args = null;
    for (int i = names.size() - 1; i >= 0; i--) {
      args = new TypeArguments(typeArgument(names.get(i)), args);
    }
    return args;
  }

  private static TypeArgument typeArgument(GraphTag name) {
    QualifiedName qualifiedName = new QualifiedName(null, name.as(TypeParameter.class).name());
    TypeReference reference = new TypeReference(qualifiedName, null);
    return new TypeArgument(reference);
  }

  private static Type type(List<GraphNode> nodes) {
    return nodes.get(0).edges().get(0).label().as(Type.class);
  }

  private static List<TypeParameter> parameterNames(List<GraphNode> nodes) {
    List<TypeParameter> list = new ArrayList<>();
    Type type = type(nodes);
    if (type.parameters() != null) {
      list.addAll(parameterNames(type.parameters().publicList()));
      list.addAll(parameterNames(type.parameters().privateList()));
    }
    return list;
  }

  private static List<TypeParameter> parameterNames(TypeParameterList list) {
    List<TypeParameter> ls = new ArrayList<>();
    while (list != null) {
      ls.add(list.head());
      list = list.tail();
    }
    return ls;
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

  private TypeParameter parameter(TypeReference reference) {
    if (reference.name().qualifier() == null) {
      String name = reference.name().name();
      if (definedParameters.stream().anyMatch(p -> p.name().equals(name))) {
        return new TypeParameter(name);
      }
    }
    return null;
  }
}
