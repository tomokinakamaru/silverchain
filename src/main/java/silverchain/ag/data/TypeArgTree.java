package silverchain.ag.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public interface TypeArgTree extends Tree {

  default boolean isTypeRef() {
    return this instanceof TypeRefTree;
  }

  default TypeRefTree asTypeRef() {
    return (TypeRefTree) this;
  }

  default boolean isWildcard() {
    return this instanceof WildcardTree;
  }

  default WildcardTree asWildcard() {
    return (WildcardTree) this;
  }

  @Override
  TypeArgTree copy();
}
