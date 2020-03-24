package silverchain.analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import silverchain.grammar.ASTNode;
import silverchain.grammar.Method;
import silverchain.grammar.Type;
import silverchain.grammar.TypeParameter;
import silverchain.graph.GraphCompileOption;
import silverchain.graph.GraphLabel;
import silverchain.graph.GraphTag;

final class GraphOption extends GraphCompileOption {

  private final Set<TypeParameter> parameters;

  GraphOption(Set<TypeParameter> parameters) {
    this.parameters = parameters;
  }

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
    if (label.is(Method.class) || label.is(Type.class)) {
      return new ArrayList<>(new ParamRefCollector(parameters).apply(label.as(ASTNode.class)));
    }
    return Collections.emptyList();
  }
}
