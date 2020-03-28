package silverchain.grammar;

import java.util.ArrayList;
import java.util.List;

public final class QualifiedName extends ASTNode2<QualifiedName, String> {

  public QualifiedName(QualifiedName qualifier, String name) {
    super(qualifier, name);
  }

  public QualifiedName qualifier() {
    return left();
  }

  public String name() {
    return right();
  }

  public List<String> list() {
    List<String> list = new ArrayList<>();
    QualifiedName name = this;
    while (name != null) {
      list.add(0, name.name());
      name = name.qualifier();
    }
    return list;
  }

  @Override
  public String toString() {
    String s = qualifier() == null ? "" : qualifier().toString() + ".";
    return s + name();
  }

  @Override
  public void accept(Visitor visitor) {
    super.accept(visitor);
    visitor.visit(this);
  }
}
