package silverchain.internal.middleware.graph.data.attribute;

import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.internal.middleware.graph.data.AttributeVisitor;

@API(status = API.Status.INTERNAL)
public class Name implements Attribute {

  private String id;

  private Name qualifier;

  public String id() {
    return id;
  }

  public void id(String id) {
    this.id = id;
  }

  public Name qualifier() {
    return qualifier;
  }

  public void qualifier(Name qualifier) {
    this.qualifier = qualifier;
  }

  @Override
  public Stream<? extends Attribute> children() {
    return Stream.of(qualifier);
  }

  @Override
  public <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }
}
