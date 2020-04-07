package silverchain.generator.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import silverchain.diagram.Diagram;
import silverchain.diagram.Label;
import silverchain.diagram.State;
import silverchain.diagram.Transition;
import silverchain.parser.Method;
import silverchain.parser.MethodParameter;
import silverchain.parser.MethodParameters;
import silverchain.parser.TypeReference;

final class Validator {

  private final Diagram diagram;

  Validator(Diagram diagram) {
    this.diagram = diagram;
  }

  void validate() {
    for (State state : diagram.states()) {
      checkTypeReferenceConflict(state);
      checkTypeReferenceMethodConflict(state);
      checkMethodConflict(state);
    }
  }

  private void checkTypeReferenceConflict(State state) {
    if (1 < state.typeReferences().size()) {
      throw new JavaEncodeError(state.typeReferences());
    }
  }

  private void checkTypeReferenceMethodConflict(State state) {
    if (0 < state.typeReferences().size() && 0 < state.transitions().size()) {
      throw new JavaEncodeError(
          state.typeReferences().get(0),
          state.transitions().stream().map(Transition::label).collect(Collectors.toList()));
    }
  }

  private void checkMethodConflict(State state) {
    Map<String, List<Label>> map = getSignatures(state);
    for (List<Label> list : map.values()) {
      if (1 < list.size()) {
        throw new JavaEncodeError(list);
      }
    }
  }

  private Map<String, List<Label>> getSignatures(State state) {
    Map<String, List<Label>> signatures = new HashMap<>();
    for (Transition transition : state.transitions()) {
      Label label = transition.label();
      String signature = getSignature(label.method());
      signatures.putIfAbsent(signature, new ArrayList<>());
      signatures.get(signature).add(label);
    }
    return signatures;
  }

  private String getSignature(Method method) {
    return method.name() + " " + method.parameters().map(this::getSignature).orElse("");
  }

  private String getSignature(MethodParameters parameters) {
    return parameters.stream().map(this::getSignature).collect(Collectors.joining(" "));
  }

  private String getSignature(MethodParameter parameter) {
    return getSignature(parameter.type());
  }

  private String getSignature(TypeReference reference) {
    return reference.referent() == null ? String.join(".", reference.name()) : "*";
  }
}
