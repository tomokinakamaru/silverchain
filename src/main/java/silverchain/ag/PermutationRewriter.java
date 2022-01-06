package silverchain.ag;

import java.util.List;
import org.apache.commons.collections4.iterators.PermutationIterator;
import org.apiguardian.api.API;
import silverchain.ag.data.ChainExprTree;
import silverchain.ag.data.ChainFactTree;
import silverchain.ag.data.ChainTermTree;
import silverchain.ag.data.GroupedExprTree;
import silverchain.ag.data.PermutationTree;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class PermutationRewriter implements TreeListener<Void> {

  @Override
  public void exit(TreeStack ancestors, PermutationTree tree, Void arg) {
    ancestors.peek().children().replace(tree, permutation(tree));
  }

  protected ChainExprTree permutation(PermutationTree tree) {
    return permutation(new PermutationIterator<>(tree));
  }

  protected ChainExprTree permutation(PermutationIterator<ChainExprTree> iterator) {
    ChainExprTree tree = new ChainExprTree();
    while (iterator.hasNext()) tree.add(term(iterator.next()));
    return tree;
  }

  protected ChainTermTree term(List<ChainExprTree> trees) {
    ChainTermTree tree = new ChainTermTree();
    trees.stream().map(this::fact).forEach(t -> tree.children().add(t));
    return tree;
  }

  protected ChainFactTree fact(ChainExprTree tree) {
    ChainExprTree expr = tree.copy();
    GroupedExprTree grouped = new GroupedExprTree();
    grouped.children().add(expr);
    ChainFactTree fact = new ChainFactTree();
    fact.children().add(grouped);
    return fact;
  }
}
