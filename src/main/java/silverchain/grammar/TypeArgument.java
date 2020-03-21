package silverchain.grammar;

public final class TypeArgument extends ASTNode1<TypeReference> {

  public TypeArgument(TypeReference reference) {
    super(reference);
  }

  public TypeReference reference() {
    return object();
  }

  @Override
  public String toString() {
    return reference().toString();
  }
}
