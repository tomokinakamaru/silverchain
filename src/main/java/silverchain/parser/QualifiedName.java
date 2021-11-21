package silverchain.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public final class QualifiedName extends ASTNode2<QualifiedName, String>
    implements Iterable<String> {

  public QualifiedName(Range range, QualifiedName qualifier, String name) {
    super(range, qualifier, name);
  }

  public Optional<QualifiedName> qualifier() {
    return Optional.ofNullable(left());
  }

  public String name() {
    return right();
  }

  @Override
  public String toString() {
    List<String> list = new ArrayList<>();
    forEach(list::add);
    return String.join(".", list);
  }

  @Override
  public Iterator<String> iterator() {
    List<String> list = new ArrayList<>();
    QualifiedName name = this;
    while (name != null) {
      list.add(0, name.name());
      name = name.qualifier().orElse(null);
    }
    return list.iterator();
  }
}
