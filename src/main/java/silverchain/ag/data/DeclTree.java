package silverchain.ag.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public interface DeclTree extends Tree {

  default boolean isAliasDecl() {
    return this instanceof AliasDeclTree;
  }

  default AliasDeclTree asAliasDecl() {
    return (AliasDeclTree) this;
  }

  default boolean isFragmentDecl() {
    return this instanceof FragmentDeclTree;
  }

  default FragmentDeclTree asFragmentDecl() {
    return (FragmentDeclTree) this;
  }

  default boolean isTypeDecl() {
    return this instanceof TypeDeclTree;
  }

  default TypeDeclTree asTypeDecl() {
    return (TypeDeclTree) this;
  }

  @Override
  DeclTree copy();
}
