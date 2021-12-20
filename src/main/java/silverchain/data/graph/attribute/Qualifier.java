package silverchain.data.graph.attribute;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.data.graph.visitor.AttributeVisitor;

@API(status = API.Status.INTERNAL)
public class Qualifier implements Attribute {

  private List<String> ids = new ArrayList<>();

  public List<String> ids() {
    return ids;
  }

  public void ids(List<String> ids) {
    this.ids = ids;
  }

  @Override
  public Stream<? extends Attribute> children() {
    return Stream.empty();
  }

  @Override
  public <R, A> R accept(AttributeVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }
}
