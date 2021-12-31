package silverchain.graph;

import org.apiguardian.api.API;
import silverchain.graph.data.Attr;
import silverchain.graph.data.TypeParams;
import silverchain.graph.data.TypeRef;
import silverchain.graph.walker.AttrVisitor;

@API(status = API.Status.INTERNAL)
public final class ParamCollector implements AttrVisitor<TypeParams, TypeParams> {

  private static final ParamCollector COLLECTOR = new ParamCollector();

  private ParamCollector() {}

  public static TypeParams collect(Attr attr) {
    return attr.accept(COLLECTOR, new TypeParams());
  }

  @Override
  public TypeParams visit(TypeRef typeRef, TypeParams arg) {
    if (typeRef.target() != null) arg.add(typeRef.target());
    return arg;
  }

  @Override
  public TypeParams defaultResult(TypeParams arg) {
    return arg;
  }
}
