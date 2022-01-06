package silverchain.ag.data;

import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nonnull;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public abstract class SetTreeImpl<T extends Tree> extends TreeImpl implements SetTree<T> {

  @Override
  public int size() {
    return children().size();
  }

  @Override
  public boolean isEmpty() {
    return children().isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return children().contains(o);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Iterator<T> iterator() {
    return children().stream().map(t -> (T) t).iterator();
  }

  @Override
  public Object[] toArray() {
    return children().toArray();
  }

  @SuppressWarnings("SuspiciousToArrayCall")
  @Override
  public <S> S[] toArray(@Nonnull S[] a) {
    return children().toArray(a);
  }

  @Override
  public boolean add(T t) {
    return children().add(t);
  }

  @Override
  public boolean remove(Object o) {
    return children().remove(o);
  }

  @Override
  public boolean containsAll(@Nonnull Collection<?> c) {
    return children().containsAll(c);
  }

  @Override
  public boolean addAll(@Nonnull Collection<? extends T> c) {
    return children().addAll(c);
  }

  @Override
  public boolean retainAll(@Nonnull Collection<?> c) {
    return children().retainAll(c);
  }

  @Override
  public boolean removeAll(@Nonnull Collection<?> c) {
    return children().removeAll(c);
  }

  @Override
  public void clear() {
    children().clear();
  }
}
