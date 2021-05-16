package silverchain.validator;

import java.util.ArrayList;
import java.util.List;
import silverchain.diagram.Diagram;

public abstract class Validator {

  private final List<Diagram> diagrams;

  protected abstract void validate(List<Diagram> diagrams);

  protected Validator(List<Diagram> diagrams) {
    this.diagrams = diagrams;
  }

  public final void validate() {
    validate(new ArrayList<>(diagrams));
  }
}
