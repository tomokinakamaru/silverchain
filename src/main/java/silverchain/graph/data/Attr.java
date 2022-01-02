package silverchain.graph.data;

import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.graph.walker.AttrListener;
import silverchain.srcmap.IntervalLists;

@API(status = API.Status.INTERNAL)
public abstract class Attr {

  private IntervalLists srcMap;

  public IntervalLists srcMap() {
    return srcMap;
  }

  public void srcMap(IntervalLists srcMap) {
    this.srcMap = srcMap;
  }

  public abstract Stream<? extends Attr> children();

  public abstract <T> void enter(AttrListener<T> listener, T arg);

  public abstract <T> void exit(AttrListener<T> listener, T arg);
}
