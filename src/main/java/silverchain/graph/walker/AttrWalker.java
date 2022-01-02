package silverchain.graph.walker;

import org.apiguardian.api.API;
import silverchain.graph.data.Attr;

@API(status = API.Status.INTERNAL)
public final class AttrWalker {

  public static <T> void walk(Attr attr, AttrListener<T> listener) {
    walk(attr, listener, null);
  }

  public static <T> void walk(Attr attr, AttrListener<T> listener, T arg) {
    attr.enter(listener, arg);
    attr.children().forEach(a -> walk(a, listener, arg));
    attr.exit(listener, arg);
  }
}
