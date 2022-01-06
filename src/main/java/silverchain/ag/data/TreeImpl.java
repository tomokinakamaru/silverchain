package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.srcmap.IntervalList;

@API(status = API.Status.INTERNAL)
public abstract class TreeImpl implements Tree {

  private TreeChildren children = new TreeChildren();

  private IntervalList srcMap = new IntervalList();

  @Override
  public Tree copy() {
    try {
      Tree tree = getClass().getDeclaredConstructor().newInstance();
      tree.children(children.copy());
      tree.srcMap(srcMap.copy());
      return tree;
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public TreeChildren children() {
    return children;
  }

  @Override
  public void children(TreeChildren children) {
    this.children = children;
  }

  @Override
  public IntervalList srcMap() {
    return srcMap;
  }

  @Override
  public void srcMap(IntervalList srcMap) {
    this.srcMap = srcMap;
  }

  @Override
  public void addSrcMap(IntervalList srcMap) {
    srcMap().addAll(srcMap);
    children.forEach(t -> t.addSrcMap(srcMap));
  }
}
