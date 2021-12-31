package silverchain.ag.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public abstract class DeclTree<SELF extends DeclTree<SELF>> extends Tree<SELF> {

  public boolean isAliasDecl() {
    return is(AliasDeclTree.class);
  }

  public AliasDeclTree asAliasDecl() {
    return as(AliasDeclTree.class);
  }

  public boolean isFragmentDecl() {
    return is(FragmentDeclTree.class);
  }

  public FragmentDeclTree asFragmentDecl() {
    return as(FragmentDeclTree.class);
  }

  public boolean isTypeDecl() {
    return is(TypeDeclTree.class);
  }

  public TypeDeclTree asTypeDecl() {
    return as(TypeDeclTree.class);
  }
}
