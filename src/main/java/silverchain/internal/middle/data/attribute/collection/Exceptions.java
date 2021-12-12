package silverchain.internal.middle.data.attribute.collection;

import silverchain.internal.middle.data.AttributeVisitor;
import silverchain.internal.middle.data.attribute.TypeReference;

public class Exceptions extends Attributes<TypeReference> {

  @Override
  public <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }
}
