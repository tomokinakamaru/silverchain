package silverchain.graph.walker;

import java.util.Iterator;
import org.apiguardian.api.API;
import silverchain.graph.data.Attr;
import silverchain.graph.data.Bound;
import silverchain.graph.data.Bounds;
import silverchain.graph.data.Exceptions;
import silverchain.graph.data.Method;
import silverchain.graph.data.Name;
import silverchain.graph.data.Param;
import silverchain.graph.data.Params;
import silverchain.graph.data.Type;
import silverchain.graph.data.TypeArgs;
import silverchain.graph.data.TypeParam;
import silverchain.graph.data.TypeParams;
import silverchain.graph.data.TypeRef;
import silverchain.graph.data.Wildcard;

@API(status = API.Status.INTERNAL)
public interface AttrVisitor<R, A> {

  default R visit(Method method, A arg) {
    return visitChildren(method, arg);
  }

  default R visit(Name name, A arg) {
    return visitChildren(name, arg);
  }

  default R visit(Param param, A arg) {
    return visitChildren(param, arg);
  }

  default R visit(Type type, A arg) {
    return visitChildren(type, arg);
  }

  default R visit(TypeParam typeParam, A arg) {
    return visitChildren(typeParam, arg);
  }

  default R visit(TypeRef typeRef, A arg) {
    return visitChildren(typeRef, arg);
  }

  default R visit(Wildcard wildcard, A arg) {
    return visitChildren(wildcard, arg);
  }

  default R visit(Bound bound, A arg) {
    return visitChildren(bound, arg);
  }

  default R visit(Bounds bounds, A arg) {
    return visitChildren(bounds, arg);
  }

  default R visit(Exceptions exceptions, A arg) {
    return visitChildren(exceptions, arg);
  }

  default R visit(Params params, A arg) {
    return visitChildren(params, arg);
  }

  default R visit(TypeArgs typeArgs, A arg) {
    return visitChildren(typeArgs, arg);
  }

  default R visit(TypeParams typeParams, A arg) {
    return visitChildren(typeParams, arg);
  }

  default R visitChildren(Attr attr, A arg) {
    Iterator<? extends Attr> iterator = attr.children().iterator();
    R result = defaultResult(arg);
    while (iterator.hasNext()) {
      R r = iterator.next().accept(this, arg);
      result = aggregate(result, r);
    }
    return result;
  }

  default R aggregate(R result1, R result2) {
    return result2 == null ? result1 : result2;
  }

  default R defaultResult(A arg) {
    return null;
  }
}
