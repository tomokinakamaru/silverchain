package silverchain.graph;

import org.apiguardian.api.API;
import silverchain.graph.data.Attr;
import silverchain.graph.data.TypeParams;
import silverchain.graph.data.TypeRef;
import silverchain.graph.walker.AttrListener;
import silverchain.graph.walker.AttrWalker;

@API(status = API.Status.INTERNAL)
public final class ParamCollector implements AttrListener<TypeParams> {

  private static final ParamCollector COLLECTOR = new ParamCollector();

  private ParamCollector() {}

  public static TypeParams collect(Attr attr) {
    TypeParams params = new TypeParams();
    AttrWalker.walk(attr, COLLECTOR, params);
    return params;
  }

  @Override
  public void enter(TypeRef attr, TypeParams arg) {
    if (attr.target() != null) arg.add(attr.target());
  }
}
