package silverchain.ag.data;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Nonnull;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class TreeChildren extends Trees {

  private Tree<?> owner;

  private Trees removed = new Trees();

  public TreeChildren(Tree<?> owner) {
    this.owner = owner;
  }

  public Tree<?> owner() {
    return owner;
  }

  public void owner(Tree<?> owner) {
    this.owner = owner;
  }

  public Trees removed() {
    return removed;
  }

  public void removed(Trees removed) {
    this.removed = removed;
  }

  public <T extends Tree<T>> T original(Class<? extends T> cls) {
    T tree = removed.find(cls);
    return tree == null ? find(cls) : tree;
  }

  @Override
  public boolean add(Tree<?> tree) {
    tree.parent(owner);
    return super.add(tree);
  }

  @Override
  public boolean addAll(@Nonnull Collection<? extends Tree<?>> c) {
    c.forEach(tree -> tree.parent(owner));
    return super.addAll(c);
  }

  @Override
  public boolean remove(Object o) {
    if (!super.remove(o)) return false;
    removed.add((Tree<?>) o);
    return true;
  }

  @Override
  public boolean retainAll(@Nonnull Collection<?> c) {
    boolean changed = false;
    for (Tree<?> t : new ArrayList<>(this)) if (!c.contains(t)) changed |= remove(t);
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
    removed.addAll(this);
    super.clear();
  }
}
