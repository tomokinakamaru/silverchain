package silverchain.internal.middle.data.attribute.collection;

import java.util.LinkedHashSet;
import java.util.stream.Stream;
import silverchain.internal.middle.data.attribute.Attribute;

public abstract class Attributes<T extends Attribute> extends LinkedHashSet<T>
    implements Attribute {

  @Override
  public Stream<? extends Attribute> children() {
    return stream();
  }
}
