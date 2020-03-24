package silverchain.grammar;

import java.util.stream.Collectors;

public final class MethodParameters extends ASTNodeN<MethodParameter, MethodParameters> {

  public MethodParameters(MethodParameter head, MethodParameters tail) {
    super(head, tail);
  }

  @Override
  public String toString() {
    return toList().stream().map(MethodParameter::toString).collect(Collectors.joining(","));
  }
}
