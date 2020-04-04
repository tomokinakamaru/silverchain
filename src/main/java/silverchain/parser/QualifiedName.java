package silverchain.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public final class QualifiedName extends ASTNode2<QualifiedName, String>
    implements Iterable<String> {

  QualifiedName(Range range, QualifiedName qualifier, String name) {
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
    return qualifier().map(q -> q + ".").orElse("") + name();
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
