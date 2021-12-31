package silverchain.ag.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public abstract class TypeArgTree<SELF extends TypeArgTree<SELF>> extends Tree<SELF> {

  public boolean isTypeRef() {
    return is(TypeRefTree.class);
  }

  public TypeRefTree asTypeRef() {
    return as(TypeRefTree.class);
  }

  public boolean isWildcard() {
    return is(WildcardTree.class);
  }

  public WildcardTree asWildcard() {
    return as(WildcardTree.class);
  }
}
