package silverchain.grammar;

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
}
