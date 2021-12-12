package silverchain.internal.middle.data.attribute.collection;

import silverchain.internal.middle.data.AttributeVisitor;
import silverchain.internal.middle.data.attribute.Parameter;

public class Parameters extends Attributes<Parameter> {

  @Override
  public <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }
}
