package silverchain.generator.diagram;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import silverchain.grammar.Method;
import silverchain.grammar.QualifiedName;
import silverchain.grammar.Type;
import silverchain.grammar.TypeParameter;
import silverchain.grammar.TypeParameters;
import silverchain.grammar.TypeReference;
import silverchain.graph.GraphEdge;
import silverchain.graph.GraphNode;

public abstract class DiagramBuilder<
    D extends Diagram<D, S, T>, S extends State<D, S, T>, T extends Transition<D, S, T>> {

  private final Supplier<D> newDiagram;

  private final Supplier<S> newState;

  private final Supplier<T> newTransition;

  private final Predicate<S> shouldNumber;

  public DiagramBuilder(
      Supplier<D> newDiagram,
      Supplier<S> newState,
      Supplier<T> newTransition,
      Predicate<S> shouldNumber) {
    this.newDiagram = newDiagram;
    this.newState = newState;
    this.newTransition = newTransition;
    this.shouldNumber = shouldNumber;
  }

  public final D build(List<GraphNode> nodes) {
    GraphNode head = nodes.get(0);
    List<GraphNode> tail = new ArrayList<>(nodes.subList(1, nodes.size()));
    D diagram = newDiagram.get();
    diagram.name = name(head);
    diagram.parameters.addAll(parameters(head));
    diagram.states.addAll(states(diagram, tail));
    return diagram;
  }

  private QualifiedName name(GraphNode node) {
    return type(node).name();
  }

  private List<TypeParameter> parameters(GraphNode node) {
    List<TypeParameter> list = new ArrayList<>();
    TypeParameters parameters = type(node).parameters();
    if (parameters != null) {
      if (parameters.publicList() != null) {
        list.addAll(parameters.publicList().list());
      }
      if (parameters.privateList() != null) {
        list.addAll(parameters.privateList().list());
      }
    }
    return list;
  }

  private Type type(GraphNode node) {
    return node.edges().get(0).label().as(Type.class);
  }

  private List<S> states(D diagram, List<GraphNode> nodes) {
    Map<GraphNode, S> map = new LinkedHashMap<>();

    for (GraphNode node : nodes) {
      S state = newState.get();
      state.isEnd = node.isEnd();
      state.diagram = diagram;
      node.tags().forEach(t -> state.parameters.add(t.as(TypeParameter.class)));
      for (GraphEdge edge : node.edges()) {
        if (edge.label().is(TypeReference.class)) {
          state.typeReferences.add(edge.label().as(TypeReference.class));
        }
      }
      map.put(node, state);
    }

    for (GraphNode node : nodes) {
      for (GraphEdge edge : node.edges()) {
        if (edge.label().is(Method.class)) {
          T transition = newTransition.get();
          transition.method = edge.label().as(Method.class);
          transition.source = map.get(node);
          transition.destination = map.get(edge.destination());
          map.get(node).transitions.add(transition);
        }
      }
    }

    int number = 0;
    for (S state : map.values()) {
      if (shouldNumber.test(state)) {
        state.number = number;
        number++;
      }
    }
    return new ArrayList<>(map.values());
  }
}
