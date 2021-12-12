package silverchain.internal.middle.graph.data.attribute.collection;

import silverchain.internal.middle.graph.data.AttributeVisitor;
import silverchain.internal.middle.graph.data.attribute.Parameter;

public class Parameters extends Attributes<Parameter> {

  @Override
  public <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }
}
