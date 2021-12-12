package silverchain.internal.middleware.graph.data.attribute;

import java.util.stream.Stream;
import silverchain.internal.middleware.graph.data.AttributeVisitor;

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
