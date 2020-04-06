package silverchain.diagram;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

final class Utility {

  private Utility() {}

  static <T> Collector<T, ?, List<T>> toArrayList() {
    return Collectors.toCollection(ArrayList::new);
  }

  static <T> List<T> collect(Iterable<T> iterable) {
    return StreamSupport.stream(iterable.spliterator(), false).collect(toArrayList());
  }
}
