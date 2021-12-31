package silverchain.java.data;

import java.util.LinkedHashSet;
import java.util.stream.Stream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public abstract class Nodes<T extends Node> extends LinkedHashSet<T> implements Node {

  @Override
  public Stream<? extends Node> children() {
    return stream();
  }
}
