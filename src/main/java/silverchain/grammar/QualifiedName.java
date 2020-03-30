package silverchain.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class QualifiedName extends ASTNode2<QualifiedName, String> {

  public QualifiedName(QualifiedName qualifier, String name) {
    super(qualifier, name);
  }

  public Optional<QualifiedName> qualifier() {
    return Optional.ofNullable(left());
  }

  public String name() {
    return right();
  }

  public List<String> list() {
    List<String> list = new ArrayList<>();
    QualifiedName name = this;
    while (name != null) {
      list.add(0, name.name());
      name = name.qualifier().orElse(null);
    }
    return list;
  }

  @Override
  public String toString() {
    return qualifier().map(q -> q + ".").orElse("") + name();
  }
}
