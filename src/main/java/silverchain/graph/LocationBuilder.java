package silverchain.graph;

import java.util.stream.Collectors;
import org.apiguardian.api.API;
import silverchain.ag.data.SrcInterval;
import silverchain.ag.data.SrcIntervals;
import silverchain.graph.data.Location;
import silverchain.graph.data.LocationGroup;

@API(status = API.Status.INTERNAL)
public final class LocationBuilder {

  private LocationBuilder() {}

  public static Location build(SrcInterval interval) {
    Location l = new Location();
    l.line(interval.begin().line());
    l.column(interval.begin().column());
    return l;
  }

  public static LocationGroup build(SrcIntervals intervals) {
    return intervals.stream()
        .map(LocationBuilder::build)
        .collect(Collectors.toCollection(LocationGroup::new));
  }
}
