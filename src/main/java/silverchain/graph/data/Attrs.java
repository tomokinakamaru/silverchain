package silverchain.graph.data;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public abstract class Attrs<T extends Attr> extends Attr implements Set<T> {

  private final Set<T> attrs = new LinkedHashSet<>();

  @Override
  public Stream<? extends Attr> children() {
    return stream().filter(Objects::nonNull);
  }

  @Override
  public int size() {
    return attrs.size();
  }

  @Override
  public boolean isEmpty() {
    return attrs.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return attrs.contains(o);
  }

  @Override
  public Iterator<T> iterator() {
    return attrs.iterator();
  }

  @Override
  public Object[] toArray() {
    return attrs.toArray();
  }

  @SuppressWarnings("SuspiciousToArrayCall")
  @Override
  public <S> S[] toArray(@Nonnull S[] a) {
    return attrs.toArray(a);
  }

  @Override
  public boolean add(T t) {
    return attrs.add(t);
  }

  @Override
  public boolean remove(Object o) {
    return attrs.remove(o);
  }

  @Override
  public boolean containsAll(@Nonnull Collection<?> c) {
    return attrs.containsAll(c);
  }

  @Override
  public boolean addAll(@Nonnull Collection<? extends T> c) {
    return attrs.addAll(c);
  }

  @Override
  public boolean retainAll(@Nonnull Collection<?> c) {
    return attrs.retainAll(c);
  }

  @Override
  public boolean removeAll(@Nonnull Collection<?> c) {
    return attrs.removeAll(c);
  }

  @Override
  public void clear() {
    attrs.clear();
  }
}
