package silverchain.graph.data;

import org.apiguardian.api.API;
import silverchain.graph.core.Attr;

@API(status = API.Status.INTERNAL)
public interface EdgeAttr extends Attr {

  default boolean isMethod() {
    return this instanceof Method;
  }

  default Method asMethod() {
    return (Method) this;
  }

  default boolean isRetType() {
    return this instanceof RetType;
  }

  default RetType asRetType() {
    return (RetType) this;
  }
}
