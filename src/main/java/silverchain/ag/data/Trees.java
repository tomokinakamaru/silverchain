package silverchain.ag.data;

import java.util.LinkedHashSet;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class Trees extends LinkedHashSet<Tree<?>> {

  public <T extends Tree<?>> T find(Class<? extends T> cls) {
    return stream().filter(cls::isInstance).findFirst().map(cls::cast).orElse(null);
  }

  public boolean replace(Tree<?> from, Tree<?> to) {
    if (remove(from)) return add(to);
    return false;
  }
}
