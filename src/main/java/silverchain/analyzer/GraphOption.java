package silverchain.analyzer;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import silverchain.grammar.ASTNode;
import silverchain.graph.GraphCompileOption;
import silverchain.graph.GraphLabel;

final class GraphOption extends GraphCompileOption {

  private final Set<String> parameters;

  GraphOption(Set<String> parameters) {
    this.parameters = parameters;
  }

  @Override
  protected boolean equals(GraphLabel label1, GraphLabel label2) {
    return Objects.equals(label1.raw(), label2.raw());
  }

  @Override
  protected int compareTo(GraphLabel label1, GraphLabel label2) {
    return label1.as(ASTNode.class).compareTo(label2.as(ASTNode.class));
  }

  @Override
  protected List<String> getTags(GraphLabel label) {
    return new ParamRefCollector(parameters).apply(label.as(ASTNode.class));
  }
}
