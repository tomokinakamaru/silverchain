package silverchain;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import silverchain.diagram.Diagram;
import silverchain.diagram.Diagrams;
import silverchain.diagram.Label;
import silverchain.diagram.State;
import silverchain.diagram.Transition;
import silverchain.parser.Range;

public final class Validator {

  private final Diagrams diagrams;

  public Validator(Diagrams diagrams) {
    this.diagrams = diagrams;
  }

  public void validate() {
    validate(new Diagrams(diagrams));
  }

  private void validate(Diagrams diagrams) {
    diagrams.forEach(this::validate);
  }

  private void validate(Diagram diagram) {
    diagram.states().forEach(this::validate);
  }

  private void validate(State state) {
    checkTypeReferenceMethodConflict(state);
  }

  private void checkTypeReferenceMethodConflict(State state) {
    List<Label> labels = state.typeReferences();
    List<Transition> transitions = state.transitions();
    if (!labels.isEmpty() && !transitions.isEmpty()) {
      Stream<Label> s1 = labels.stream();
      Stream<Label> s2 = transitions.stream().map(Transition::label);
      throwError(concat(s1, s2).collect(toList()));
    }
  }

  private void throwError(List<Label> labels) {
    throw new ValidationError("Conflict: %s", stringify(labels));
  }

  private String stringify(Collection<Label> labels) {
    return labels.stream().map(this::stringify).collect(joining(", "));
  }

  private String stringify(Label label) {
    return label.node().toString() + "#" + stringify(label.ranges());
  }

  private String stringify(List<Range> ranges) {
    return ranges.stream().map(this::stringify).collect(joining(","));
  }

  private String stringify(Range range) {
    return range.begin().toString();
  }
}
