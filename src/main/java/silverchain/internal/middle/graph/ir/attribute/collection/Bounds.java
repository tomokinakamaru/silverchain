package silverchain.internal.middle.graph.ir.attribute.collection;

import silverchain.internal.middle.graph.ir.AttributeVisitor;
import silverchain.internal.middle.graph.ir.attribute.TypeReference;

public class Bounds extends Attributes<TypeReference> {

  @Override
  public <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }
}
