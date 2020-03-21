package silverchain.grammar;

public final class TypeReference extends ASTNode2<QualifiedName, TypeArguments> {

  public TypeReference(QualifiedName name, TypeArguments arguments) {
    super(name, arguments);
  }

  public QualifiedName name() {
    return left();
  }

  public TypeArguments arguments() {
    return right();
  }

  @Override
  public String toString() {
    String s = arguments() == null ? "" : "<" + arguments().toString() + ">";
    return name().toString() + s;
  }
}
