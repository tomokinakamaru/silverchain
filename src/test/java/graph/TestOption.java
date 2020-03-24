package graph;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import silverchain.graph.GraphCompileOption;
import silverchain.graph.GraphLabel;
import silverchain.graph.GraphTag;

final class TestOption extends GraphCompileOption {

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
    return label1.as(String.class).compareTo(label2.as(String.class));
  }

  @Override
  protected List<Object> getTags(GraphLabel label) {
    String s = label.as(String.class);
    return s.contains(":") ? Arrays.asList(s.split(":")[1].split(",")) : Collections.emptyList();
  }
}
