package silverchain.grammar;

public abstract class ASTNodeN<T, S extends ASTNodeN<T, S>> extends ASTNode2<T, S> {

  ASTNodeN(T head, S tail) {
    super(head, tail);
  }

  public final T head() {
    return left();
  }

  public final S tail() {
    return right();
  }
}
