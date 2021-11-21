package silverchain.parser;

public final class TypeArguments extends ASTNodeN<TypeArgument, TypeArguments> {

  public TypeArguments(Range range, TypeArgument head, TypeArguments tail) {
    super(range, head, tail);
  }
}
