package silverchain.grammar;

import java.util.ArrayList;
import java.util.List;

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

  public final List<T> toList() {
    List<T> list = new ArrayList<>();
    ASTNodeN<T, S> node = this;
    while (node != null) {
      list.add(node.head());
      node = node.tail();
    }
    return list;
  }
}
