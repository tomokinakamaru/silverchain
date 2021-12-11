package silverchain.internal.middle.graph.ir.attribute.collection;

import silverchain.internal.middle.graph.ir.AttributeVisitor;
import silverchain.internal.middle.graph.ir.attribute.TypeArgument;

public class TypeArguments extends Attributes<TypeArgument> {

  @Override
  public <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }
}
