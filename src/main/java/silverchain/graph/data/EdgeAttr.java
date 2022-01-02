package silverchain.graph.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public abstract class EdgeAttr extends Attr {

  public boolean isMethod() {
    return this instanceof Method;
  }

  public Method asMethod() {
    return (Method) this;
  }

  public boolean isRetType() {
    return this instanceof RetType;
  }

  public RetType asRetType() {
    return (RetType) this;
  }
}
