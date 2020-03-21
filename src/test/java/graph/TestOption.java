package graph;

import java.util.List;
import silverchain.graph.GraphCompileOption;
import silverchain.graph.GraphLabel;

final class TestOption extends GraphCompileOption {

  @Override
  protected boolean equals(GraphLabel label1, GraphLabel label2) {
    return label1.raw().equals(label2.raw());
  }

  @Override
  protected int compareTo(GraphLabel label1, GraphLabel label2) {
    return label1.as(TestLabel.class).compareTo(label2.as(TestLabel.class));
  }

  @Override
  protected List<String> getTags(GraphLabel label) {
    return label.as(TestLabel.class).tags;
  }
}
