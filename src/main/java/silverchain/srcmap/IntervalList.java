package silverchain.srcmap;

import java.util.ArrayList;
import java.util.stream.Collectors;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class IntervalList extends ArrayList<Interval> {

  public IntervalList copy() {
    return stream().map(Interval::copy).collect(Collectors.toCollection(IntervalList::new));
  }

  @Override
  public String toString() {
    return stream().map(Interval::toString).collect(Collectors.joining("-"));
  }
}
