package silverchain.process.graph.builder;

import org.antlr.v4.runtime.Token;
import org.apiguardian.api.API;
import silverchain.data.graph.location.Location;
import silverchain.data.graph.location.Locations;

@API(status = API.Status.INTERNAL)
public final class LocationBuilder {

  private LocationBuilder() {}

  public static Locations build(Token token) {
    Locations locations = new Locations();
    Location location = new Location();
    location.line(token.getLine());
    location.column(token.getCharPositionInLine());
    return locations;
  }
}
