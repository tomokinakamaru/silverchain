package silverchain.ag.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public abstract class ChainElemTree<SELF extends ChainElemTree<SELF>> extends Tree<SELF> {

  public boolean isFragmentRef() {
    return is(FragmentRefTree.class);
  }

  public FragmentRefTree asFragmentRef() {
    return as(FragmentRefTree.class);
  }

  public boolean isGroupedExpr() {
    return is(GroupedExprTree.class);
  }

  public GroupedExprTree asGroupedExpr() {
    return as(GroupedExprTree.class);
  }

  public boolean isMethod() {
    return is(MethodTree.class);
  }

  public MethodTree asMethod() {
    return as(MethodTree.class);
  }

  public boolean isPermutation() {
    return is(PermutationTree.class);
  }

  public PermutationTree asPermutation() {
    return as(PermutationTree.class);
  }
}
