package silverchain.internal.middle.graph.data.attribute;

import silverchain.internal.middle.graph.data.location.Locations;

public abstract class Label implements Attribute {

  private Locations locations = new Locations();

  public Locations locations() {
    return locations;
  }

  public void locations(Locations locations) {
    this.locations = locations;
  }
}
