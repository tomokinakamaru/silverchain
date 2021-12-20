package silverchain.data.graph.attribute.collection;

import org.apiguardian.api.API;
import silverchain.data.graph.attribute.TypeArgument;
import silverchain.data.graph.visitor.AttributeVisitor;

@API(status = API.Status.INTERNAL)
public class TypeArguments extends Attributes<TypeArgument> {

  @Override
  public <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }
}
