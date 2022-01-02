package silverchain.graph.data;

import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.graph.walker.AttrListener;
import silverchain.graph.walker.AttrVisitor;
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

  public abstract <R, A> R accept(AttrVisitor<R, A> visitor, A arg);

  public abstract <T> void enter(AttrListener<T> listener, T arg);

  public abstract <T> void exit(AttrListener<T> listener, T arg);
}
