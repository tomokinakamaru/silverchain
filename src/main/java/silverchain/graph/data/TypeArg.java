package silverchain.graph.data;

import org.apiguardian.api.API;
import silverchain.ag.data.TypeArgTree;
import silverchain.graph.core.Attr;

@API(status = API.Status.INTERNAL)
public interface TypeArg extends Attr {

  static TypeArg build(TypeArgTree tree) {
    if (tree == null) return null;
    return tree.isTypeRef() ? TypeRef.build(tree.asTypeRef()) : Wildcard.build(tree.asWildcard());
  }

  default boolean isTypeRef() {
    return this instanceof TypeRef;
  }

  default TypeRef asTypeRef() {
    return (TypeRef) this;
  }

  default boolean isWildcard() {
    return this instanceof Wildcard;
  }

  default Wildcard asWildcard() {
    return (Wildcard) this;
  }
}
