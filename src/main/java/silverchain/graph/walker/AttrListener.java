package silverchain.graph.walker;

import org.apiguardian.api.API;
import silverchain.graph.data.Bound;
import silverchain.graph.data.Bounds;
import silverchain.graph.data.Exceptions;
import silverchain.graph.data.Method;
import silverchain.graph.data.Name;
import silverchain.graph.data.Param;
import silverchain.graph.data.Params;
import silverchain.graph.data.RetType;
import silverchain.graph.data.Type;
import silverchain.graph.data.TypeArgs;
import silverchain.graph.data.TypeParam;
import silverchain.graph.data.TypeParams;
import silverchain.graph.data.TypeRef;
import silverchain.graph.data.Wildcard;

@API(status = API.Status.INTERNAL)
public interface AttrListener {

  default void enter(Bound attr) {}

  default void exit(Bound attr) {}

  default void enter(Bounds attr) {}

  default void exit(Bounds attr) {}

  default void enter(Exceptions attr) {}

  default void exit(Exceptions attr) {}

  default void enter(Method attr) {}

  default void exit(Method attr) {}

  default void enter(Name attr) {}

  default void exit(Name attr) {}

  default void enter(Param attr) {}

  default void exit(Param attr) {}

  default void enter(Params attr) {}

  default void exit(Params attr) {}

  default void enter(RetType attr) {}

  default void exit(RetType attr) {}

  default void enter(TypeArgs attr) {}

  default void exit(TypeArgs attr) {}

  default void enter(TypeParam attr) {}

  default void exit(TypeParam attr) {}

  default void enter(TypeParams attr) {}

  default void exit(TypeParams attr) {}

  default void enter(TypeRef attr) {}

  default void exit(TypeRef attr) {}

  default void enter(Type attr) {}

  default void exit(Type attr) {}

  default void enter(Wildcard attr) {}

  default void exit(Wildcard attr) {}
}
