package silverchain.parser;

import java.util.Optional;

public final class MethodParameters extends ASTNode2<TypeParameterList, FormalParameters> {

  public MethodParameters(Range range, TypeParameterList left, FormalParameters right) {
    super(range, left, right);
  }

  public Optional<TypeParameterList> localTypeParameters() {
    return Optional.ofNullable(left());
  }

  public Optional<FormalParameters> formalParameters() {
    return Optional.ofNullable(right());
  }

  @Override
  public String toString() {
    String s = "(" + formalParameters().map(ASTNodeN::toString).orElse("") + ")";
    return localTypeParameters().map(p -> "<" + p + ">").orElse("") + s;
  }
}
