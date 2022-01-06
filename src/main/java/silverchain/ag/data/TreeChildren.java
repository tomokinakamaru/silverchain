package silverchain.ag.data;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Nonnull;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class TreeChildren extends Trees {

  private final Trees removed = new Trees();

  public <T extends Tree> T findOriginal(Class<? extends T> cls) {
    T tree = removed.find(cls);
    return tree == null ? find(cls) : tree;
  }

  public boolean replace(Tree from, Tree to) {
    if (remove(from)) return add(to);
    return false;
  }

  @Override
  public TreeChildren copy() {
    TreeChildren children = new TreeChildren();
    children.addAll(super.copy());
    children.removed.addAll(removed.copy());
    return children;
  }

  @Override
  public boolean remove(Object o) {
    if (!super.remove(o)) return false;
    removed.add((Tree) o);
    return true;
  }

  @Override
  public boolean retainAll(@Nonnull Collection<?> c) {
    boolean changed = false;
    for (Tree t : new ArrayList<>(this)) if (!c.contains(t)) changed |= remove(t);
    return changed;
  }

  @Override
  public boolean removeAll(@Nonnull Collection<?> c) {
    boolean changed = false;
    for (Object o : c) changed |= remove(o);
    return changed;
  }

  @Override
  public void clear() {
    for (Tree t : new ArrayList<>(this)) remove(t);
  }
}
