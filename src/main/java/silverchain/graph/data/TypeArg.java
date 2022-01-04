package silverchain.graph.data;

import org.apiguardian.api.API;
import silverchain.ag.data.TypeArgTree;

@API(status = API.Status.INTERNAL)
public abstract class TypeArg extends Attr {

  public static TypeArg build(TypeArgTree<?> tree) {
    if (tree == null) return null;
    return tree.isTypeRef() ? TypeRef.build(tree.asTypeRef()) : Wildcard.build(tree.asWildcard());
  }

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
