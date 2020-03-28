package silverchain.grammar;

import java.util.List;

public final class TypeArgument extends ASTNode1<TypeReference> {

  public TypeArgument(TypeReference reference) {
    super(reference);
  }

  public TypeReference reference() {
    return object();
  }

  public List<TypeParameter> referents() {
    return reference().referents();
  }

  @Override
  public String toString() {
    return reference().toString();
  }

  @Override
  public void accept(Visitor visitor) {
    super.accept(visitor);
    visitor.visit(this);
  }
}
