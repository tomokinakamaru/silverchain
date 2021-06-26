package silverchain.diagram;

import java.util.ArrayList;

public final class Diagrams extends ArrayList<Diagram> {

  public Diagrams() {}

  public Diagrams(Diagrams diagrams) {
    addAll(diagrams);
  }
}
