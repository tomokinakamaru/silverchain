package silverchain.ag.data;

import java.util.Collections;
import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;
import silverchain.srcmap.IntervalList;

@API(status = API.Status.INTERNAL)
public abstract class Tree<SELF extends Tree<SELF>> {

  private Tree<?> parent;

  private TreeChildren children = new TreeChildren(this);

  private IntervalList srcMap = new IntervalList();

  public abstract <T> void enter(TreeListener<T> listener, T arg);

  public abstract <T> void exit(TreeListener<T> listener, T arg);

  public Tree<?> parent() {
    return parent;
  }

  public void parent(Tree<?> parent) {
    this.parent = parent;
  }

  public TreeChildren children() {
    return children;
  }

  public void children(TreeChildren children) {
    this.children = children;
  }

  public IntervalList srcMap() {
    return srcMap;
  }

  public void srcMap(IntervalList srcMap) {
    this.srcMap = srcMap;
  }

  public void add(IntervalList target) {
    srcMap.addAll(target);
    children.forEach(t -> t.add(target));
  }

  @SuppressWarnings("unchecked")
  public SELF with(Tree<?>... trees) {
    Collections.addAll(children, trees);
    return (SELF) this;
  }

  @SuppressWarnings("unchecked")
  public SELF copy() {
    try {
      SELF tree = (SELF) getClass().getDeclaredConstructor().newInstance();
      children.forEach(t -> tree.children().add(t.copy()));
      srcMap.forEach(l -> tree.srcMap().add(l));
      return tree;
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }

  public <T> boolean is(Class<T> cls) {
    return cls.isInstance(this);
  }

  public <T> T as(Class<T> cls) {
    return cls.cast(this);
  }
}
