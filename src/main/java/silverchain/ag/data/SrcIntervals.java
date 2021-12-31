package silverchain.ag.data;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class SrcIntervals extends LinkedHashSet<SrcInterval> {

  public SrcInterval first() {
    return iterator().next();
  }

  @Override
  public String toString() {
    return stream().map(SrcInterval::toString).collect(Collectors.joining(">"));
  }
}
