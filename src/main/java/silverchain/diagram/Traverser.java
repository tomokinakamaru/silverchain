package silverchain.diagram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

final class Traverser<T> {

  private final Queue<T> queue = new LinkedList<>();

  private final List<T> queued = new ArrayList<>();

  Traverser(T item) {
    enqueue(item);
  }

  Traverser(Collection<T> items) {
    items.forEach(this::enqueue);
  }

  boolean hasNext() {
    return !queue.isEmpty();
  }

  T next() {
    return queue.remove();
  }

  void enqueue(T item) {
    if (item != null && !queued.contains(item)) {
      queue.add(item);
      queued.add(item);
    }
  }

  void enqueue(Collection<T> items) {
    items.forEach(this::enqueue);
  }

  List<T> queued() {
    return queued;
  }
}
