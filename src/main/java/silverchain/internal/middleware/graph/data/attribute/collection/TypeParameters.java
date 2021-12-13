package silverchain.internal.middleware.graph.data.attribute.collection;

import org.apiguardian.api.API;
import silverchain.internal.middleware.graph.data.AttributeVisitor;
import silverchain.internal.middleware.graph.data.attribute.TypeParameter;

@API(status = API.Status.INTERNAL)
public class TypeParameters extends Attributes<TypeParameter> {

  @Override
  public <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }
}
