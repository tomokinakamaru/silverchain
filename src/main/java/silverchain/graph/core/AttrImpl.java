package silverchain.graph.core;

import org.apiguardian.api.API;
import silverchain.srcmap.IntervalLists;

@API(status = API.Status.INTERNAL)
public abstract class AttrImpl implements Attr {

  private IntervalLists srcMap = new IntervalLists();

  @Override
  public IntervalLists srcMap() {
    return srcMap;
  }

  @Override
  public void srcMap(IntervalLists srcMap) {
    this.srcMap = srcMap;
  }
}
