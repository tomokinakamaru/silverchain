package silverchain.graph.walker;

import org.apiguardian.api.API;
import silverchain.graph.data.Attr;

@API(status = API.Status.INTERNAL)
public final class AttrWalker {

  private AttrWalker() {}

  public static void walk(AttrListener listener, Attr attr) {
    attr.enter(listener);
    attr.children().forEach(a -> walk(listener, a));
    attr.exit(listener);
  }
}
