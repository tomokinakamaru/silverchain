package silverchain.graph.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public abstract class TypeArg extends Attr {

  public boolean isTypeRef() {
    return this instanceof TypeRef;
  }

  public TypeRef asTypeRef() {
    return (TypeRef) this;
  }

  public boolean isWildcard() {
    return this instanceof Wildcard;
  }

  public Wildcard asWildcard() {
    return (Wildcard) this;
  }
}
