package silverchain.internal.middleware.graph.builder;

import org.antlr.v4.runtime.Token;
import org.apiguardian.api.API;
import silverchain.internal.frontend.data.VirtualToken;
import silverchain.internal.middleware.graph.data.location.Location;
import silverchain.internal.middleware.graph.data.location.Locations;

@API(status = API.Status.INTERNAL)
public final class LocationBuilder {

  private LocationBuilder() {}

  public static Locations build(Token token) {
    Locations locations = new Locations();
    Location location = new Location();
    location.line(token.getLine());
    location.column(token.getCharPositionInLine());
    if (token instanceof VirtualToken) {
      VirtualToken v = (VirtualToken) token;
      locations.addAll(build(v.subToken()));
    }
    return locations;
  }
}
