package silverchain.grammar;

public final class TypeReference extends ASTNode2<QualifiedName, TypeArguments> {

  private TypeParameter referent;

  public TypeReference(QualifiedName name, TypeArguments arguments) {
    super(name, arguments);
  }

  public QualifiedName name() {
    return left();
  }

  public TypeArguments arguments() {
    return right();
  }

  public void referent(TypeParameter referent) {
    this.referent = referent;
  }

  public TypeParameter referent() {
    return referent;
  }

  @Override
  public String toString() {
    String s = arguments() == null ? "" : "<" + arguments().toString() + ">";
    return name().toString() + s;
  }
}
