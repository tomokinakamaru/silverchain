package silverchain.generator;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import silverchain.diagram.Diagram;
import silverchain.diagram.Label;
import silverchain.diagram.State;
import silverchain.diagram.Transition;
import silverchain.parser.Method;
import silverchain.parser.MethodParameter;
import silverchain.parser.MethodParameters;
import silverchain.parser.Range;
import silverchain.parser.TypeReference;

final class JavaValidator {

  private JavaValidator() {}

  static void validate(Diagram diagram) {
    diagram.states().forEach(JavaValidator::validate);
  }

  private static void validate(State state) {
    checkTypeReferenceConflict(state);
    checkTypeReferenceMethodConflict(state);
    checkMethodConflict(state);
  }

  private static void checkTypeReferenceConflict(State state) {
    if (1 < state.typeReferences().size()) {
      throwError(state.typeReferences());
    }
  }

  private static void checkTypeReferenceMethodConflict(State state) {
    if (0 < state.typeReferences().size() && 0 < state.transitions().size()) {
      Label label = state.typeReferences().get(0);
      List<Label> labels = state.transitions().stream().map(Transition::label).collect(toList());
      throwError(label, labels);
    }
  }

  private static void checkMethodConflict(State state) {
    for (List<Label> labels : getSignatures(state)) {
      if (1 < labels.size()) {
        throwError(labels);
      }
    }
  }

  private static void throwError(Label label, List<Label> labels) {
    labels.add(0, label);
    throwError(labels);
  }

  private static void throwError(List<Label> labels) {
    throw new EncodeError("Conflict: %s", encode(labels));
  }

  private static String encode(Collection<Label> labels) {
    return labels.stream().map(JavaValidator::encode).collect(joining(", "));
  }

  private static String encode(Label label) {
    return label.node().toString() + "#" + encode(label.ranges());
  }

  private static String encode(List<Range> ranges) {
    return ranges.stream().map(JavaValidator::encode).collect(joining(","));
  }

  private static String encode(Range range) {
    return range.begin().toString();
  }

  private static Collection<List<Label>> getSignatures(State state) {
    Map<String, List<Label>> signatures = new HashMap<>();
    for (Transition t : state.transitions()) {
      String s = getSignature(t.label());
      signatures.putIfAbsent(s, new ArrayList<>());
      signatures.get(s).add(t.label());
    }
    return signatures.values();
  }

  private static String getSignature(Label label) {
    return getSignature(label.method());
  }

  private static String getSignature(Method method) {
    return method.name() + ":" + method.parameters().map(JavaValidator::getSignature).orElse("");
  }

  private static String getSignature(MethodParameters parameters) {
    return parameters.stream().map(JavaValidator::getSignature).collect(joining(" "));
  }

  private static String getSignature(MethodParameter parameter) {
    return getSignature(parameter.type());
  }

  private static String getSignature(TypeReference reference) {
    return reference.referent() == null ? String.join(".", reference.name()) : "*";
  }
}
