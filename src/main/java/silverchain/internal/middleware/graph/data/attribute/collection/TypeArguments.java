package silverchain.internal.middleware.graph.data.attribute.collection;

import silverchain.internal.middleware.graph.data.AttributeVisitor;
import silverchain.internal.middleware.graph.data.attribute.TypeArgument;

public class TypeArguments extends Attributes<TypeArgument> {

  @Override
  public <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }
}
