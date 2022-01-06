package silverchain.ag.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public interface ChainElemTree extends Tree {

  default boolean isFragmentRef() {
    return this instanceof FragmentRefTree;
  }

  default FragmentRefTree asFragmentRef() {
    return (FragmentRefTree) this;
  }

  default boolean isGroupedExpr() {
    return this instanceof GroupedExprTree;
  }

  default GroupedExprTree asGroupedExpr() {
    return (GroupedExprTree) this;
  }

  default boolean isMethod() {
    return this instanceof MethodTree;
  }

  default MethodTree asMethod() {
    return (MethodTree) this;
  }

  default boolean isPermutation() {
    return this instanceof PermutationTree;
  }

  default PermutationTree asPermutation() {
    return (PermutationTree) this;
  }

  @Override
  ChainElemTree copy();
}
