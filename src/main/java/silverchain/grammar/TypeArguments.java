package silverchain.grammar;

import java.util.stream.Collectors;

public final class TypeArguments extends ASTNodeN<TypeArgument, TypeArguments> {

  public TypeArguments(TypeArgument head, TypeArguments tail) {
    super(head, tail);
  }

  @Override
  public String toString() {
    return toList().stream().map(TypeArgument::toString).collect(Collectors.joining(","));
  }
}
