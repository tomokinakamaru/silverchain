package silverchain.grammar;

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

  @Override
  public String toString() {
    String s = qualifier() == null ? "" : qualifier().toString() + ".";
    return s + name();
  }
}
