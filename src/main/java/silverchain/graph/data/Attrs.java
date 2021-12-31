package silverchain.graph.data;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public abstract class Attrs<T extends Attr> extends LinkedHashSet<T> implements Attr {

  @Override
  public Stream<? extends Attr> children() {
    return stream().filter(Objects::nonNull);
  }
}
