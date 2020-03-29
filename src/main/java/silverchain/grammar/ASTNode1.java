package silverchain.grammar;

abstract class ASTNode1<T> extends ASTNode {

  private final T object;

  ASTNode1(T object) {
    this.object = object;
  }

  final T object() {
    return object;
  }
}
