package silverchain.analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import silverchain.grammar.ASTNode;
import silverchain.grammar.Method;
import silverchain.grammar.Type;
import silverchain.grammar.TypeParameters;
import silverchain.graph.GraphCompileOption;
import silverchain.graph.GraphLabel;
import silverchain.graph.GraphTag;

final class GraphOption extends GraphCompileOption {

  @Override
  protected boolean equals(GraphLabel label1, GraphLabel label2) {
    return label1.raw().equals(label2.raw());
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
      return new ArrayList<>(label.as(Method.class).referents());
    }
    if (label.is(Type.class)) {
      TypeParameters parameters = label.as(Type.class).parameters();
      if (parameters != null && parameters.publicList() != null) {
        return new ArrayList<>(parameters.publicList().toList());
      }
    }
    return Collections.emptyList();
  }
}
