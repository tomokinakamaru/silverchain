package silverchain.parser;

public final class TypeReferences extends ASTNodeN<TypeReference, TypeReferences> {

  TypeReferences(Range range, TypeReference head, TypeReferences tail) {
    super(range, head, tail);
  }
}
