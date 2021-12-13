package silverchain.internal.middleware.graph.data.attribute;

import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.internal.middleware.graph.data.AttributeVisitor;

@API(status = API.Status.INTERNAL)
public class Wildcard extends TypeArgument {

  private boolean upperBound;

  private TypeReference bound;

  public boolean upperBound() {
    return upperBound;
  }

  public void upperBound(boolean upperBound) {
    this.upperBound = upperBound;
  }

  public TypeReference bound() {
    return bound;
  }

  public void bound(TypeReference bound) {
    this.bound = bound;
  }

  @Override
  public Stream<? extends Attribute> children() {
    return Stream.of(bound);
  }

  @Override
  public <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }
}
