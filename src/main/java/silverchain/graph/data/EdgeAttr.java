package silverchain.graph.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public interface EdgeAttr extends Attr {

  default boolean isMethod() {
    return this instanceof Method;
  }

  default Method asMethod() {
    return (Method) this;
  }

  default boolean isTypeRef() {
    return this instanceof TypeRef;
  }

  default TypeRef asTypeRef() {
    return (TypeRef) this;
  }
}
