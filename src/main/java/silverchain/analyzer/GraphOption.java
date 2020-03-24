package silverchain.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import silverchain.grammar.ASTNode;
import silverchain.grammar.Method;
import silverchain.grammar.MethodParameter;
import silverchain.grammar.MethodParameters;
import silverchain.grammar.Type;
import silverchain.grammar.TypeArgument;
import silverchain.grammar.TypeArguments;
import silverchain.grammar.TypeParameter;
import silverchain.grammar.TypeReference;
import silverchain.graph.GraphCompileOption;
import silverchain.graph.GraphLabel;
import silverchain.graph.GraphTag;

final class GraphOption extends GraphCompileOption {

  @Override
  protected boolean equals(GraphLabel label1, GraphLabel label2) {
    return Objects.equals(label1.raw(), label2.raw());
  }

  @Override
  protected boolean equals(GraphTag tag1, GraphTag tag2) {
    return tag1.raw().equals(tag2.raw());
  }

  @Override
  protected int compareTo(GraphLabel label1, GraphLabel label2) {
    return label1.as(ASTNode.class).compareTo(label2.as(ASTNode.class));
  }

  @Override
  protected List<Object> getTags(GraphLabel label) {
    if (label.is(Method.class)) {
      return new ArrayList<>(getTags(label.as(Method.class)));
    }
    if (label.is(Type.class)) {
      return getTags(label.as(Type.class));
    }
    return Collections.emptyList();
  }

  private List<TypeParameter> getTags(Method method) {
    return method.parameters() == null ? Collections.emptyList() : getTags(method.parameters());
  }

  private List<TypeParameter> getTags(MethodParameters parameters) {
    return parameters
        .toList()
        .stream()
        .map(this::getTags)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  private List<TypeParameter> getTags(MethodParameter parameter) {
    return getTags(parameter.type());
  }

  private List<TypeParameter> getTags(TypeReference reference) {
    List<TypeParameter> parameters = new ArrayList<>();
    if (reference.referent() != null) {
      parameters.add(reference.referent());
    }
    if (reference.arguments() != null) {
      parameters.addAll(getTags(reference.arguments()));
    }
    return parameters;
  }

  private List<TypeParameter> getTags(TypeArguments arguments) {
    return arguments
        .toList()
        .stream()
        .map(this::getTags)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  private List<TypeParameter> getTags(TypeArgument argument) {
    return getTags(argument.reference());
  }

  private List<Object> getTags(Type type) {
    if (type.parameters() != null && type.parameters().publicList() != null) {
      return new ArrayList<>(type.parameters().publicList().toList());
    }
    return Collections.emptyList();
  }
}
