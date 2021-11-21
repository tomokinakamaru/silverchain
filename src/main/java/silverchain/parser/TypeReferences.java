package silverchain.parser;

public final class TypeReferences extends ASTNodeN<TypeReference, TypeReferences> {

  public TypeReferences(Range range, TypeReference left, TypeReferences right) {
    super(range, left, right);
  }
}
