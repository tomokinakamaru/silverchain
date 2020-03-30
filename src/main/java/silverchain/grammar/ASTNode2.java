package silverchain.grammar;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import silverchain.graph.Graph;

abstract class ASTNode2<T, S> extends ASTNode {

  private final T left;

  private final S right;

  ASTNode2(T left, S right) {
    this.left = left;
    this.right = right;
  }

  final T left() {
    return left;
  }

  final S right() {
    return right;
  }

  Graph reduce(Graph graph1, Graph graph2) {
    return null;
  }

  @Override
  public List<TypeParameter> typeParameters() {
    return Stream.of(left, right)
        .filter(o -> o instanceof ASTNode)
        .map(o -> (ASTNode) o)
        .map(ASTNode::typeParameters)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  @Override
  public void resolveReferences(List<TypeParameter> typeParameters) {
    Stream.of(left, right)
        .filter(o -> o instanceof ASTNode)
        .map(o -> (ASTNode) o)
        .forEach(n -> n.resolveReferences(typeParameters));
  }

  @Override
  public Graph graph() {
    Graph graph1 = left instanceof ASTNode ? ((ASTNode) left).graph() : null;
    Graph graph2 = right instanceof ASTNode ? ((ASTNode) right).graph() : null;
    if (graph2 == null) {
      return graph1;
    }
    return reduce(graph1, graph2);
  }
}
