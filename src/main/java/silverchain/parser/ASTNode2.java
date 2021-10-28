package silverchain.parser;

import static silverchain.diagram.Builders.join;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import silverchain.diagram.Diagram;

abstract class ASTNode2<T, S> extends ASTNode {

  protected T left;

  protected S right;

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

  Diagram reduce(Diagram diagram1, Diagram diagram2) {
    return join(diagram1, diagram2);
  }

  @Override
  public List<TypeParameter> typeParameters() {
    return flatMap(ASTNode::typeParameters).collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public Diagram diagram(Map<String, QualifiedName> importMap) {
    return map(n -> n.diagram(importMap)).reduce(this::reduce).orElse(null);
  }

  @Override
  public List<TypeParameter> referents() {
    return flatMap(ASTNode::referents).distinct().collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public void validate() {
    each(ASTNode::validate);
  }

  @Override
  void resolveReferences(List<TypeParameter> typeParameters, Map<String, QualifiedName> importMap) {
    each(n -> n.resolveReferences(typeParameters, importMap));
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
