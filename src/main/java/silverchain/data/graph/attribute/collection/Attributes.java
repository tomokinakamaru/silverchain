package silverchain.data.graph.attribute.collection;

import java.util.LinkedHashSet;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.data.graph.attribute.Attribute;

@API(status = API.Status.INTERNAL)
public abstract class Attributes<T extends Attribute> extends LinkedHashSet<T>
    implements Attribute {

  @Override
  public Stream<? extends Attribute> children() {
    return stream();
  }
}
