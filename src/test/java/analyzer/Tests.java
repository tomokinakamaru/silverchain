package analyzer;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import silverchain.graph.GraphNode;
import silverchain.parser.Grammar;
import silverchain.parser.Grammars;
import silverchain.parser.ParseException;
import silverchain.parser.Parser;
import utility.GraphTester;

final class Tests {

  @Test
  void test1() throws ParseException {
    test("Foo[T,S;U]:").edge(0, 1, "Foo[T,S;U]").noEdge(1).noTags(0).tags(1, "T", "S").endNodes(1);
  }

  @Test
  void test2() throws ParseException {
    test("Foo: bar(Bar bar) Baz;")
        .edge(0, 1, "Foo")
        .edge(1, 2, "bar(Bar bar)")
        .edge(2, 3, "Baz")
        .noEdge(3)
        .noTags(0, 1, 2, 3)
        .endNodes(3);
  }

  @Test
  void test3() throws ParseException {
    test("Foo: bar(Bar bar) baz(Baz baz) Foo;")
        .edge(0, 1, "Foo")
        .edge(1, 2, "bar(Bar bar)")
        .edge(2, 3, "baz(Baz baz)")
        .edge(3, 4, "Foo")
        .noEdge(4)
        .noTags(0, 1, 2, 3, 4)
        .endNodes(4);
  }

  @Test
  void test4() throws ParseException {
    test("Foo: bar(Bar bar) | baz(Baz baz) Foo;")
        .edge(0, 1, "Foo")
        .edge(1, 2, "bar(Bar bar)", "baz(Baz baz)")
        .edge(2, 3, "Foo")
        .noEdge(3)
        .noTags(0, 1, 2, 3)
        .endNodes(3);
  }

  @Test
  void test5() throws ParseException {
    test("Foo: bar(Bar bar) Foo; baz(Baz baz) Foo;")
        .edge(0, 1, "Foo")
        .edge(1, 2, "bar(Bar bar)", "baz(Baz baz)")
        .edge(2, 3, "Foo")
        .noEdge(3)
        .noTags(0, 1, 2, 3)
        .endNodes(3);
  }

  @Test
  void test6() throws ParseException {
    test("Foo: bar(Bar bar)* Bar; baz(Baz baz){1,2} Baz;")
        .edge(0, 1, "Foo")
        .edge(1, 2, "Bar")
        .edge(1, 3, "bar(Bar bar)")
        .edge(1, 4, "baz(Baz baz)")
        .edge(3, 3, "bar(Bar bar)")
        .edge(3, 2, "Bar")
        .edge(4, 5, "baz(Baz baz)")
        .edge(5, 2, "Baz")
        .noEdge(2)
        .noTags(0, 1, 2, 3, 4, 5)
        .endNodes(2);
  }

  @Test
  void test7() throws ParseException {
    test("Foo[;T]: bar(Bar[T] bar) Baz[T];")
        .edge(0, 1, "Foo[;T]")
        .edge(1, 2, "bar(Bar[T] bar)")
        .edge(2, 3, "Baz[T]")
        .noEdge(3)
        .noTags(0, 1)
        .tags(2, "T")
        .tags(3, "T")
        .endNodes(3);
  }

  @Test
  void test8() throws ParseException {
    test("Foo: bar(Bar bar);")
        .edge(0, 1, "Foo")
        .edge(1, 2, "bar(Bar bar)")
        .noEdge(2)
        .noTags(0, 1, 2)
        .endNodes(2);
  }

  private GraphTester test(String text) throws ParseException {
    Grammars grammars = new Parser(new ByteArrayInputStream(text.getBytes())).grammars();
    List<List<GraphNode>> list = new ArrayList<>();
    for (Grammar grammar : grammars) {
      grammar.resolveReferences(grammar.typeParameters());
      list.add(grammar.graph().compile());
    }
    return new GraphTester(list.get(0));
  }
}
