package silverchain.data.graph.attribute;

import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.data.graph.visitor.AttributeVisitor;

@API(status = API.Status.INTERNAL)
public class Name implements Attribute {

  private String id;

  private Qualifier qualifier;

  public String id() {
    return id;
  }

  public void id(String id) {
    this.id = id;
  }

  public Qualifier qualifier() {
    return qualifier;
  }

  public void qualifier(Qualifier qualifier) {
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
