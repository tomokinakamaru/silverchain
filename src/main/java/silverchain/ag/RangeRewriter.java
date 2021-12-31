package silverchain.ag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.ag.data.ChainElemTree;
import silverchain.ag.data.ChainFactTree;
import silverchain.ag.data.ChainTermTree;
import silverchain.ag.data.QuantifierTree;
import silverchain.ag.data.RangeNMTree;
import silverchain.ag.data.RangeNTree;
import silverchain.ag.data.RangeNXTree;
import silverchain.ag.data.RangeTree;
import silverchain.ag.data.Repeat01Tree;
import silverchain.ag.data.Repeat0XTree;
import silverchain.ag.walker.TreeListener;

@API(status = API.Status.INTERNAL)
public class RangeRewriter implements TreeListener<Void> {

  @Override
  public void exit(ChainTermTree tree, Void arg) {
    List<ChainFactTree> trees = rewrite(tree);
    tree.children().clear();
    tree.children().addAll(trees);
  }

  protected List<ChainFactTree> rewrite(ChainTermTree tree) {
    return tree.stream().flatMap(this::rewrite).collect(Collectors.toCollection(ArrayList::new));
  }

  protected Stream<ChainFactTree> rewrite(ChainFactTree tree) {
    if (tree.quantifier() != null) {
      QuantifierTree<?> q = tree.quantifier();
      if (q.isRange()) return rewrite(tree.elem(), q.asRange());
    }
    return Stream.of(tree);
  }

  protected Stream<ChainFactTree> rewrite(ChainElemTree<?> elem, RangeTree<?> range) {
    if (range.isRangeN()) return rewrite(elem, range.asRangeN());
    if (range.isRangeNX()) return rewrite(elem, range.asRangeNX());
    if (range.isRangeNM()) return rewrite(elem, range.asRangeNM());
    throw new RuntimeException();
  }

  protected Stream<ChainFactTree> rewrite(ChainElemTree<?> elem, RangeNTree range) {
    ChainFactTree t = new ChainFactTree().with(elem);
    return repeat(t, range.n());
  }

  protected Stream<ChainFactTree> rewrite(ChainElemTree<?> elem, RangeNXTree range) {
    ChainFactTree t1 = new ChainFactTree().with(elem.copy());
    ChainFactTree t2 = new ChainFactTree().with(elem.copy(), new Repeat0XTree());
    return Stream.concat(repeat(t1, range.min()), Stream.of(t2));
  }

  protected Stream<ChainFactTree> rewrite(ChainElemTree<?> elem, RangeNMTree range) {
    ChainFactTree t1 = new ChainFactTree().with(elem.copy());
    ChainFactTree t2 = new ChainFactTree().with(elem.copy(), new Repeat01Tree());
    return Stream.concat(repeat(t1, range.min()), repeat(t2, range.max() - range.max()));
  }

  protected Stream<ChainFactTree> repeat(ChainFactTree tree, int n) {
    return IntStream.range(0, n).mapToObj(i -> tree.copy());
  }
}
