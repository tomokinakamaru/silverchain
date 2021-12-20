package silverchain.data.graph.attribute;

import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.data.graph.visitor.AttributeVisitor;

@API(status = API.Status.INTERNAL)
public class ReturnType extends Label {

  private TypeReference type;

  public TypeReference type() {
    return type;
  }

  public void type(TypeReference type) {
    this.type = type;
  }

  @Override
  public Stream<? extends Attribute> children() {
    return Stream.of(type);
  }

  @Override
  public <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }
}
