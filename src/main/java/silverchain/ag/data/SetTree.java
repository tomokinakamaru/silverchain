package silverchain.ag.data;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nonnull;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public interface SetTree<T extends Tree<? extends T>> extends Set<T> {

  TreeChildren children();

  @Override
  default int size() {
    return children().size();
  }

  @Override
  default boolean isEmpty() {
    return children().isEmpty();
  }

  @Override
  default boolean contains(Object o) {
    return children().contains(o);
  }

  @SuppressWarnings("unchecked")
  @Override
  default Iterator<T> iterator() {
    return children().stream().map(t -> (T) t).iterator();
  }

  @Override
  default Object[] toArray() {
    return children().toArray();
  }

  @SuppressWarnings("SuspiciousToArrayCall")
  @Override
  default <S> S[] toArray(@Nonnull S[] a) {
    return children().toArray(a);
  }

  @Override
  default boolean add(T t) {
    return children().add(t);
  }

  @Override
  default boolean remove(Object o) {
    return children().remove(o);
  }

  @Override
  default boolean containsAll(@Nonnull Collection<?> c) {
    return children().containsAll(c);
  }

  @Override
  default boolean addAll(@Nonnull Collection<? extends T> c) {
    return children().addAll(c);
  }

  @Override
  default boolean retainAll(@Nonnull Collection<?> c) {
    return children().retainAll(c);
  }

  @Override
  default boolean removeAll(@Nonnull Collection<?> c) {
    return children().removeAll(c);
  }

  @Override
  default void clear() {
    children().clear();
  }
}
