package silverchain.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

abstract class ASTNodeN<T, S extends ASTNodeN<T, S>> extends ASTNode2<T, S> implements Iterable<T> {

  ASTNodeN(Range range, T head, S tail) {
    super(range, head, tail);
  }

  String separator() {
    return ",";
  }

  public final Stream<T> stream() {
    return StreamSupport.stream(spliterator(), false);
  }

  @Override
  public final Iterator<T> iterator() {
    List<T> list = new ArrayList<>();
    ASTNodeN<T, S> node = this;
    while (node != null) {
      list.add(node.left());
      node = node.right();
    }
    return list.iterator();
  }

  @Override
  public final String toString() {
    return stream().map(Object::toString).collect(Collectors.joining(separator()));
  }
}
