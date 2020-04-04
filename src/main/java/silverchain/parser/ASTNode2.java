package silverchain.parser;

import static silverchain.graph.GraphBuilders.join;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import silverchain.graph.Graph;

abstract class ASTNode2<T, S> extends ASTNode {

  private final T left;

  private final S right;

  ASTNode2(Range range, T left, S right) {
    super(range);
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
    return join(graph1, graph2);
  }

  @Override
  public Set<TypeParameter> typeParameters() {
    return flatMap(ASTNode::typeParameters).collect(Collectors.toCollection(LinkedHashSet::new));
  }

  @Override
  public Graph graph() {
    return map(ASTNode::graph).reduce(this::reduce).orElse(null);
  }

  @Override
  public Set<TypeParameter> referents() {
    return flatMap(ASTNode::referents).collect(Collectors.toCollection(LinkedHashSet::new));
  }

  @Override
  void resolveReferences(Set<TypeParameter> typeParameters) {
    each(n -> n.resolveReferences(typeParameters));
  }

  private <U> Stream<U> flatMap(Function<ASTNode, Collection<U>> function) {
    return childNodes().flatMap(n -> function.apply(n).stream());
  }

  private <U> Stream<U> map(Function<ASTNode, U> function) {
    return childNodes().map(function);
  }

  private void each(Consumer<ASTNode> consumer) {
    childNodes().forEach(consumer);
  }

  private Stream<ASTNode> childNodes() {
    return Stream.of(left, right).filter(o -> o instanceof ASTNode).map(o -> (ASTNode) o);
  }
}
