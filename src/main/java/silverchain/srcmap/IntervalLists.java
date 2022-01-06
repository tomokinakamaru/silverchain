package silverchain.srcmap;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class IntervalLists extends LinkedHashSet<IntervalList> {

  public IntervalLists copy() {
    return stream().map(IntervalList::copy).collect(Collectors.toCollection(IntervalLists::new));
  }

  @Override
  public String toString() {
    return stream().map(IntervalList::toString).collect(Collectors.joining("|"));
  }
}
