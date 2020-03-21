package graph;

import static java.lang.String.join;

import java.util.Arrays;
import java.util.List;
import silverchain.graph.Graph;
import silverchain.graph.GraphBuilder;

final class TestLabel implements Comparable<TestLabel> {

  final String text;

  final List<String> tags;

  private TestLabel(String text, String... tags) {
    this.text = text;
    this.tags = Arrays.asList(tags);
  }

  static Graph atom(String text, String... tags) {
    return GraphBuilder.atom(new TestLabel(text, tags));
  }

  @Override
  public int compareTo(TestLabel label) {
    if (text.equals(label.text)) {
      return join(" ", tags).compareTo(join(" ", label.tags));
    }
    return text.compareTo(label.text);
  }
}
