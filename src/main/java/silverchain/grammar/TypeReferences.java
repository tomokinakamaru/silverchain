package silverchain.grammar;

public final class TypeReferences extends ASTNodeN<TypeReference, TypeReferences> {

  public TypeReferences(TypeReference head, TypeReferences tail) {
    super(head, tail);
  }

  @Override
  String separator() {
    return ",";
  }
}
