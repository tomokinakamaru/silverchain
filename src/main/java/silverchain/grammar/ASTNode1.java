package silverchain.grammar;

public abstract class ASTNode1<T> extends ASTNode {

  private final T object;

  ASTNode1(T object) {
    this.object = object;
  }

  final T object() {
    return object;
  }

  @Override
  public final void accept(Visitor visitor) {
    if (object instanceof ASTNode) {
      ((ASTNode) object).accept(visitor);
    }
    super.accept(visitor);
  }
}
