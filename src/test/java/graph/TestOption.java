package graph;

import java.util.Arrays;
import java.util.Collections;
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
    return label1.as(String.class).compareTo(label2.as(String.class));
  }

  @Override
  protected List<String> getTags(GraphLabel label) {
    String s = label.as(String.class);
    return s.contains(":") ? Arrays.asList(s.split(":")[1].split(",")) : Collections.emptyList();
  }
}
