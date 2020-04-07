package silverchain.generator;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;
import silverchain.diagram.Diagram;
import silverchain.diagram.Label;
import silverchain.diagram.State;
import silverchain.diagram.Transition;
import silverchain.parser.Method;
import silverchain.parser.MethodParameter;
import silverchain.parser.MethodParameters;
import silverchain.parser.QualifiedName;
import silverchain.parser.Range;
import silverchain.parser.TypeParameter;
import silverchain.parser.TypeParameterBound;
import silverchain.parser.TypeReference;
import silverchain.parser.TypeReferences;

public final class JavaGenerator extends Generator {

  private Map<State, Integer> numbers = new HashMap<>();

  public JavaGenerator(List<Diagram> diagrams) {
    super(diagrams);
  }

  @Override
  void generate(List<Diagram> diagrams) {
    diagrams.forEach(this::generate);
  }

  private void generate(Diagram diagram) {
    validate(diagram);
    assignNumbers(diagram);
    generateInterface(diagram);
    for (State state : diagram.states()) {
      if (numbers.containsKey(state)) {
        generateInterface(state);
        generateImplementation(state);
      }
    }
  }

  private void assignNumbers(Diagram diagram) {
    int n = 0;
    for (State state : diagram.states()) {
      if (!state.isEnd()) {
        numbers.put(state, n);
        n++;
      }
    }
  }

  private void generateInterface(State state) {
    beginFile(filePath(interfaceQualifiedName(state)));
    write(packageDeclaration(interfacePackageName(state)));

    write(interfaceModifier(state));
    write("interface ");
    write(interfaceName(state));
    write(encodeAsDeclaration(state.typeParameters()));
    write(" {\n");

    for (Transition transition : state.transitions()) {
      write("\n  ");
      write(stateMethodDeclaration(transition));
      write(";\n");
    }

    write("}\n");
    endFile();
  }

  private void generateImplementation(State state) {
    beginFile(filePath(implementationQualifiedName(state)));
    write(packageDeclaration(implementationPackageName(state)));

    write("@SuppressWarnings({\"rawtypes\", \"unchecked\"})\nclass ");
    write(implementationName(state));
    write(encodeAsDeclaration(state.typeParameters()));
    write(" implements ");
    write(reference(state));
    write(" {\n\n  ");

    write(interfaceQualifiedName(state.diagram()));
    write(" action;\n\n  ");

    write(implementationName(state));
    write("(");
    write(interfaceQualifiedName(state.diagram()));
    write(" action) {\n    this.action = action;\n  }\n");

    for (Transition transition : state.transitions()) {
      write("\n  @Override\n  public ");
      write(stateMethodDeclaration(transition));
      write(" {\n");
      write(stateMethodBodyListenerInvocation(transition));
      write(stateMethodBodyReturnState(transition));
      write("  }\n");
    }

    write("}\n");
    endFile();
  }

  private void generateInterface(Diagram diagram) {
    beginFile(filePath(interfaceQualifiedName(diagram)));
    write(packageDeclaration(interfacePackageName(diagram)));

    write("interface ");
    write(interfaceName(diagram));
    write(encodeAsDeclaration(diagram.typeParameters()));
    write(" {\n");

    for (State state : diagram.states()) {
      if (numbers.containsKey(state)) {
        for (Transition transition : state.transitions()) {
          write("\n  default ");
          write(actionMethodDeclaration(transition, true));
          write(" {\n    ");
          write(actionMethodDefaultBody(transition));
          write("\n  }");
          write("\n");
        }
      }
    }

    Set<Method> encodedMethods = new HashSet<>();
    for (State state : diagram.states()) {
      if (numbers.containsKey(state)) {
        for (Transition transition : state.transitions()) {
          if (!encodedMethods.contains(transition.label().method())) {
            write("\n  ");
            write(actionMethodDeclaration(transition, false));
            write(";\n");
            encodedMethods.add(transition.label().method());
          }
        }
      }
    }

    write("}\n");
    endFile();
  }

  private String filePath(String name) {
    return name.replaceAll("\\.", "/") + ".java";
  }

  private String interfaceQualifiedName(State state) {
    return qualifiedName(interfacePackageName(state), interfaceName(state));
  }

  private String interfacePackageName(State state) {
    int number = numbers.get(state);
    String qualifier = state.diagram().name().qualifier().map(JavaGenerator::encode).orElse("");
    return number == 0 ? qualifier : qualifiedName(qualifier, "state" + number);
  }

  private String interfaceName(State state) {
    return (numbers.get(state) == 0 ? "I" : "") + state.diagram().name().name();
  }

  private String qualifiedName(String qualifier, String name) {
    return qualifier.isEmpty() ? name : qualifier + "." + name;
  }

  private String packageDeclaration(String name) {
    return name.isEmpty() ? "" : "package " + name + ";\n\n";
  }

  private String interfaceModifier(State state) {
    return numbers.get(state) == 0 ? "" : "public ";
  }

  private String stateMethodDeclaration(Transition transition) {
    String decl = reference(transition.destination());
    decl += " " + encodeAsDeclaration(transition.label().method());
    if (transition.typeParameters().isEmpty()) {
      return decl;
    }
    return encodeAsDeclaration(transition.typeParameters()) + " " + decl;
  }

  private String reference(State state) {
    if (numbers.containsKey(state)) {
      return interfaceQualifiedName(state) + encodeAsArgument(state.typeParameters());
    }
    Optional<Label> label = state.typeReferences().stream().findFirst();
    return label.map(l -> encode(l.typeReference())).orElse("void");
  }

  private String implementationQualifiedName(State state) {
    return qualifiedName(implementationPackageName(state), implementationName(state));
  }

  private String implementationPackageName(State state) {
    return state.diagram().name().qualifier().map(JavaGenerator::encode).orElse("");
  }

  private String implementationName(State state) {
    return state.diagram().name().name() + numbers.get(state);
  }

  private String interfaceName(Diagram diagram) {
    return "I" + diagram.name().name() + "Action";
  }

  private String interfacePackageName(Diagram diagram) {
    return diagram.name().qualifier().map(JavaGenerator::encode).orElse("");
  }

  private String interfaceQualifiedName(Diagram diagram) {
    return qualifiedName(interfacePackageName(diagram), interfaceName(diagram));
  }

  private String actionMethodDeclaration(Transition transition, boolean withPrefix) {
    State dst = transition.destination();
    String type = numbers.containsKey(dst) ? "void" : reference(dst);
    String prefix = withPrefix ? ("state" + numbers.get(transition.source()) + "$") : "";
    return type + " " + prefix + encodeAsDeclaration(transition.label().method());
  }

  private String actionMethodInvocation(Transition transition, boolean withPrefix) {
    String prefix = withPrefix ? ("state" + numbers.get(transition.source()) + "$") : "";
    return prefix + encodeAsInvocation(transition.label().method()) + ";";
  }

  private String actionMethodDefaultBody(Transition transition) {
    Optional<Label> label = transition.destination().typeReferences().stream().findFirst();
    String prefix = label.map(r -> "return ").orElse("");
    return prefix + actionMethodInvocation(transition, false);
  }

  private String stateMethodBodyListenerInvocation(Transition transition) {
    Optional<Label> label = transition.destination().typeReferences().stream().findFirst();
    String type = label.map(r -> "return ").orElse("");
    return "    " + type + "this.action." + actionMethodInvocation(transition, true) + "\n";
  }

  private String stateMethodBodyReturnState(Transition transition) {
    if (numbers.containsKey(transition.destination())) {
      String name = implementationQualifiedName(transition.destination());
      return "    return new " + name + "(this.action);\n";
    }
    return "";
  }

  private static String encodeAsDeclaration(List<TypeParameter> parameters) {
    return parameters.isEmpty() ? "" : "<" + csv(parameters.stream(), p -> encode(p, true)) + ">";
  }

  private static String encodeAsArgument(List<TypeParameter> parameters) {
    return parameters.isEmpty() ? "" : "<" + csv(parameters.stream(), p -> encode(p, false)) + ">";
  }

  private static String encode(TypeParameter parameter, boolean includeBound) {
    return parameter.name()
        + (includeBound ? parameter.bound().map(JavaGenerator::encode).orElse("") : "");
  }

  private static String encode(TypeParameterBound bound) {
    return " extends " + encode(bound.reference());
  }

  private static String encodeAsDeclaration(Method method) {
    return encode(method, true);
  }

  private static String encodeAsInvocation(Method method) {
    return encode(method, false);
  }

  private static String encode(Method method, boolean includeType) {
    return method.name()
        + "("
        + method.parameters().map(p -> encode(p, includeType)).orElse("")
        + ")";
  }

  private static String encode(MethodParameters parameters, boolean includeType) {
    return csv(parameters.stream(), p -> encode(p, includeType));
  }

  private static String encode(MethodParameter parameter, boolean includeType) {
    return includeType ? encode(parameter.type()) + " " + parameter.name() : parameter.name();
  }

  private static String encode(TypeReference reference) {
    return encode(reference.name()) + reference.arguments().map(JavaGenerator::encode).orElse("");
  }

  private static String encode(TypeReferences arguments) {
    return "<" + csv(arguments.stream(), JavaGenerator::encode) + ">";
  }

  private static String encode(QualifiedName name) {
    return String.join(".", name);
  }

  private static <T> String csv(Stream<T> stream, Function<T, String> function) {
    return stream.map(function).collect(joining(", "));
  }

  // Validation ------------------------------------------------------------------------------------
  private static void validate(Diagram diagram) {
    diagram.states().forEach(JavaGenerator::validate);
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
    getSignatures(state).stream().filter(ls -> 1 < ls.size()).forEach(JavaGenerator::throwError);
  }

  private static Collection<List<Label>> getSignatures(State state) {
    Map<String, List<Label>> signatures = new HashMap<>();
    for (Transition transition : state.transitions()) {
      Label label = transition.label();
      String signature = getSignature(label.method());
      signatures.putIfAbsent(signature, new ArrayList<>());
      signatures.get(signature).add(label);
    }
    return signatures.values();
  }

  private static String getSignature(Method method) {
    return method.name() + ":" + method.parameters().map(JavaGenerator::getSignature).orElse("");
  }

  private static String getSignature(MethodParameters parameters) {
    return parameters.stream().map(JavaGenerator::getSignature).collect(joining(" "));
  }

  private static String getSignature(MethodParameter parameter) {
    return getSignature(parameter.type());
  }

  private static String getSignature(TypeReference reference) {
    return reference.referent() == null ? String.join(".", reference.name()) : "*";
  }

  private static void throwError(List<Label> labels) {
    throw new EncodeError("Conflict: %s", encode(labels));
  }

  private static void throwError(Label label, List<Label> labels) {
    throw new EncodeError("Conflict: %s", encode(construct(label, labels)));
  }

  private static String encode(Collection<Label> labels) {
    return labels.stream().map(JavaGenerator::encode).collect(joining(", "));
  }

  private static String encode(Label label) {
    return label.node().toString() + "#" + encode(label.ranges());
  }

  private static String encode(List<Range> ranges) {
    return ranges.stream().map(JavaGenerator::encode).collect(joining(","));
  }

  private static String encode(Range range) {
    return range.begin().toString();
  }

  private static List<Label> construct(Label label, List<Label> labels) {
    List<Label> list = new ArrayList<>(labels);
    list.add(0, label);
    return list;
  }
}
