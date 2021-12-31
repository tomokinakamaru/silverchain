package silverchain.graph.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public interface EdgeAttr extends Attr {

  default boolean isMethod() {
    return this instanceof Method;
  }

  default boolean isRetType() {
    return this instanceof RetType;
  }

  default Method asMethod() {
    return (Method) this;
  }

  default RetType asRetType() {
    return (RetType) this;
  }
}
