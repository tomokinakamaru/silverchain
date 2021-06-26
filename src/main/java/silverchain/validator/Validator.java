package silverchain.validator;

import silverchain.diagram.Diagrams;

public abstract class Validator {

  private final Diagrams diagrams;

  protected abstract void validate(Diagrams diagrams);

  protected Validator(Diagrams diagrams) {
    this.diagrams = diagrams;
  }

  public final void validate() {
    validate(new Diagrams(diagrams));
  }
}
