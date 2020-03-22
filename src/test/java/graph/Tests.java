package graph;

import static silverchain.graph.GraphBuilder.atom;
import static silverchain.graph.GraphBuilder.join;
import static silverchain.graph.GraphBuilder.merge;
import static silverchain.graph.GraphBuilder.repeat;

import java.util.List;
import org.junit.jupiter.api.Test;
import silverchain.graph.Graph;
import silverchain.graph.GraphCompileOption;
import silverchain.graph.GraphEdge;
import silverchain.graph.GraphLabel;
import silverchain.graph.GraphNode;
import utility.GraphTester;

final class Tests {

  private static final GraphCompileOption option = new TestOption();

  @Test
  void testAtom() {
    test(atom("foo")).edge(0, 1, "foo").noEdge(1).endNodes(1);
  }

  @Test
  void testRepeat() {
    test(repeat(atom("foo"), null, null)).edge(0, 0, "foo").endNodes(0);

    test(repeat(atom("foo"), 1, null)).edge(0, 1, "foo").edge(1, 1, "foo").endNodes(1);

    test(repeat(atom("foo"), 1, 3))
        .edge(0, 1, "foo")
        .edge(1, 2, "foo")
        .edge(2, 3, "foo")
        .noEdge(3)
        .endNodes(1, 2, 3);
  }

  @Test
  void testJoin() {
    test(join(atom("foo"), atom("bar"))).edge(0, 1, "foo").edge(1, 2, "bar").noEdge(2).endNodes(2);
  }

  @Test
  void testMerge() {
    test(merge(atom("foo"), atom("bar"))).edge(0, 1, "foo", "bar").noEdge(1).endNodes(1);
  }

  @Test
  void testTagPropagation() {
    test(repeat(atom("foo:T"), 0, null))
        .edge(0, 1, "foo:T")
        .edge(1, 1, "foo:T")
        .noTags(0)
        .tags(1, "T")
        .endNodes(0, 1);

    test(join(merge(atom("foo:T"), atom("foo:S")), atom("bar")))
        .edge(0, 1, "foo:S")
        .edge(0, 2, "foo:T")
        .edge(1, 3, "bar")
        .edge(2, 4, "bar")
        .noEdge(3, 4)
        .noTags(0)
        .tags(1, "S")
        .tags(2, "T")
        .tags(3, "S")
        .tags(4, "T")
        .endNodes(3, 4);
  }

  @Test
  void testMisc() {
    List<GraphNode> nodes = repeat(atom("foo"), null, null).compile(option);

    GraphEdge edge = nodes.get(0).edges().get(0);
    assert edge.source() == edge.destination();

    GraphLabel label = edge.label();
    assert label.is(String.class);
    assert !label.is(Integer.class);
  }

  private GraphTester test(Graph graph) {
    return new GraphTester(graph.compile(option));
  }
}
