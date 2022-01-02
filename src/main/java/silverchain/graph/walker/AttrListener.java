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
public interface AttrListener<T> {

  default void enter(Bound attr, T arg) {}

  default void exit(Bound attr, T arg) {}

  default void enter(Bounds attr, T arg) {}

  default void exit(Bounds attr, T arg) {}

  default void enter(Exceptions attr, T arg) {}

  default void exit(Exceptions attr, T arg) {}

  default void enter(Method attr, T arg) {}

  default void exit(Method attr, T arg) {}

  default void enter(Name attr, T arg) {}

  default void exit(Name attr, T arg) {}

  default void enter(Param attr, T arg) {}

  default void exit(Param attr, T arg) {}

  default void enter(Params attr, T arg) {}

  default void exit(Params attr, T arg) {}

  default void enter(RetType attr, T arg) {}

  default void exit(RetType attr, T arg) {}

  default void enter(TypeArgs attr, T arg) {}

  default void exit(TypeArgs attr, T arg) {}

  default void enter(TypeParam attr, T arg) {}

  default void exit(TypeParam attr, T arg) {}

  default void enter(TypeParams attr, T arg) {}

  default void exit(TypeParams attr, T arg) {}

  default void enter(TypeRef attr, T arg) {}

  default void exit(TypeRef attr, T arg) {}

  default void enter(Type attr, T arg) {}

  default void exit(Type attr, T arg) {}

  default void enter(Wildcard attr, T arg) {}

  default void exit(Wildcard attr, T arg) {}
}
