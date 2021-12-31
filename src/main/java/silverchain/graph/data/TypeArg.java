package silverchain.graph.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public abstract class TypeArg implements Attr {

  public boolean isTypeRef() {
    return this instanceof TypeRef;
  }

  public boolean isWildcard() {
    return this instanceof Wildcard;
  }

  public TypeRef asTypeRef() {
    return (TypeRef) this;
  }

  public Wildcard asWildcard() {
    return (Wildcard) this;
  }
}
