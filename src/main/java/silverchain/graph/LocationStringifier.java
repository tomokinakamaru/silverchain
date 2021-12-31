package silverchain.graph;

import java.util.stream.Collectors;
import org.apiguardian.api.API;
import silverchain.graph.data.Location;
import silverchain.graph.data.LocationGroup;
import silverchain.graph.data.LocationGroups;

@API(status = API.Status.INTERNAL)
public final class LocationStringifier {

  private LocationStringifier() {}

  public static String stringify(LocationGroups locations) {
    return locations.stream().map(LocationStringifier::stringify).collect(Collectors.joining(","));
  }

  public static String stringify(LocationGroup locations) {
    return locations.stream().map(LocationStringifier::stringify).collect(Collectors.joining(","));
  }

  public static String stringify(Location location) {
    return String.format("L%dC%d", location.line(), location.column());
  }
}
