package silverchain.internal.middleware.graph.data.attribute;

import org.apiguardian.api.API;
import silverchain.internal.middleware.graph.data.location.Locations;

@API(status = API.Status.INTERNAL)
public abstract class Label implements Attribute {

  private Locations locations = new Locations();

  public Locations locations() {
    return locations;
  }

  public void locations(Locations locations) {
    this.locations = locations;
  }
}
