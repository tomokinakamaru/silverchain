package diagram;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.Test;
import silverchain.diagram.Diagram;
import silverchain.diagram.Label;
import silverchain.parser.AgParser;
import silverchain.parser.Grammar;
import silverchain.parser.adapter.Parser;

final class Tests {

  @Test
  void test1() {
    test("foo.Foo {}")
        .name("foo.Foo")
        .typeParameterCount(0)
        .stateCount(1)
        .end(0)
        .stateTypeParameterCount(0, 0)
        .stateTypeReferenceCount(0, 0)
        .transitionCount(0, 0);
  }

  @Test
  void test2() {
    test("Foo { void bar(Bar bar); }")
        .name("Foo")
        .typeParameterCount(0)
        .stateCount(2)
        .end(1)
        .stateTypeParameterCount(0, 0)
        .stateTypeParameterCount(1, 0)
        .stateTypeReferenceCount(0, 0)
        .stateTypeReferenceCount(1, 0)
        .transitionCount(0, 1)
        .transitionCount(1, 0)
        .transitionDestination(0, 0, 1)
        .transitionTypeParameterCount(0, 0, 0)
        .transitionLabelRangeCount(0, 0, 1);
  }

  @Test
  void test3() {
    test("Foo { baz.Baz bar(Bar bar); }")
        .name("Foo")
        .typeParameterCount(0)
        .stateCount(2)
        .end(1)
        .stateTypeParameterCount(0, 0)
        .stateTypeParameterCount(1, 0)
        .stateTypeReferenceCount(0, 0)
        .stateTypeReferenceCount(1, 1)
        .stateTypeReference(1, 0, "baz.Baz")
        .transitionCount(0, 1)
        .transitionCount(1, 0)
        .transitionDestination(0, 0, 1)
        .transitionTypeParameterCount(0, 0, 0)
        .transitionLabelRangeCount(0, 0, 1);
  }

  @Test
  void test4() {
    test("Foo { Baz bar(Bar bar) baz(Baz baz)*; }")
        .name("Foo")
        .typeParameterCount(0)
        .stateCount(2)
        .end(1)
        .stateTypeParameterCount(0, 0)
        .stateTypeParameterCount(1, 0)
        .stateTypeReferenceCount(0, 0)
        .stateTypeReferenceCount(1, 1)
        .stateTypeReference(1, 0, "Baz")
        .transitionCount(0, 1)
        .transitionCount(1, 1)
        .transitionDestination(0, 0, 1)
        .transitionDestination(1, 0, 1)
        .transitionTypeParameterCount(0, 0, 0)
        .transitionTypeParameterCount(1, 0, 0)
        .transitionLabelRangeCount(0, 0, 1)
        .transitionLabelRangeCount(1, 0, 1);
  }

  @Test
  void test6() {
    test("Foo { Baz bar(Bar bar) baz(Baz baz)+; }")
        .name("Foo")
        .typeParameterCount(0)
        .stateCount(3)
        .end(2)
        .stateTypeParameterCount(0, 0)
        .stateTypeParameterCount(1, 0)
        .stateTypeParameterCount(2, 0)
        .stateTypeReferenceCount(0, 0)
        .stateTypeReferenceCount(1, 0)
        .stateTypeReferenceCount(2, 1)
        .stateTypeReference(2, 0, "Baz")
        .transitionCount(0, 1)
        .transitionCount(1, 1)
        .transitionCount(2, 1)
        .transitionDestination(0, 0, 1)
        .transitionDestination(1, 0, 2)
        .transitionDestination(2, 0, 2)
        .transitionTypeParameterCount(0, 0, 0)
        .transitionTypeParameterCount(1, 0, 0)
        .transitionTypeParameterCount(2, 0, 0)
        .transitionLabelRangeCount(0, 0, 1)
        .transitionLabelRangeCount(1, 0, 1)
        .transitionLabelRangeCount(2, 0, 1);
  }

  @Test
  void test7() {
    test("Foo { Baz bar(Bar bar) baz(Baz baz)?; }")
        .name("Foo")
        .typeParameterCount(0)
        .stateCount(3)
        .end(1, 2)
        .stateTypeParameterCount(0, 0)
        .stateTypeParameterCount(1, 0)
        .stateTypeParameterCount(2, 0)
        .stateTypeReferenceCount(0, 0)
        .stateTypeReferenceCount(1, 1)
        .stateTypeReferenceCount(2, 1)
        .stateTypeReference(1, 0, "Baz")
        .stateTypeReference(2, 0, "Baz")
        .transitionCount(0, 1)
        .transitionCount(1, 1)
        .transitionCount(2, 0)
        .transitionDestination(0, 0, 1)
        .transitionDestination(1, 0, 2)
        .transitionTypeParameterCount(0, 0, 0)
        .transitionTypeParameterCount(1, 0, 0)
        .transitionLabelRangeCount(0, 0, 1)
        .transitionLabelRangeCount(1, 0, 1);
  }

  @Test
  void test8() {
    test("Foo { Qux bar(Bar bar) | baz(Baz baz); }")
        .name("Foo")
        .typeParameterCount(0)
        .stateCount(2)
        .end(1)
        .stateTypeParameterCount(0, 0)
        .stateTypeParameterCount(1, 0)
        .stateTypeReferenceCount(0, 0)
        .stateTypeReferenceCount(1, 1)
        .stateTypeReference(1, 0, "Qux")
        .transitionCount(0, 2)
        .transitionCount(1, 0)
        .transitionDestination(0, 0, 1)
        .transitionDestination(0, 1, 1)
        .transitionTypeParameterCount(0, 0, 0)
        .transitionTypeParameterCount(0, 1, 0)
        .transitionLabelRangeCount(0, 0, 1)
        .transitionLabelRangeCount(0, 1, 1);
  }

  @Test
  void test9() {
    test("Foo { Baz foo(); Bar foo(); }")
        .name("Foo")
        .typeParameterCount(0)
        .stateCount(2)
        .stateTypeParameterCount(0, 0)
        .stateTypeParameterCount(1, 0)
        .stateTypeReferenceCount(0, 0)
        .stateTypeReferenceCount(1, 2)
        .stateTypeReference(1, 0, "Bar")
        .stateTypeReference(1, 1, "Baz")
        .transitionCount(0, 1)
        .transitionCount(1, 0)
        .transitionDestination(0, 0, 1)
        .transitionTypeParameterCount(0, 0, 0)
        .transitionLabelRangeCount(0, 0, 2);
  }

  @Test
  void test10() {
    test("Foo { Qux bar(Bar bar); Qux baz(Baz baz); }")
        .name("Foo")
        .typeParameterCount(0)
        .stateCount(2)
        .end(1)
        .stateTypeParameterCount(0, 0)
        .stateTypeParameterCount(1, 0)
        .stateTypeReferenceCount(0, 0)
        .stateTypeReferenceCount(1, 1)
        .stateTypeReference(1, 0, "Qux")
        .transitionCount(0, 2)
        .transitionCount(1, 0)
        .transitionDestination(0, 0, 1)
        .transitionDestination(0, 1, 1)
        .transitionTypeParameterCount(0, 0, 0)
        .transitionTypeParameterCount(0, 1, 0)
        .transitionLabelRangeCount(0, 0, 1)
        .transitionLabelRangeCount(0, 1, 1);
  }

  @Test
  void test11() {
    test("Foo<T;S> { Qux<T,S> bar(T t) baz(S s); }")
        .name("Foo")
        .typeParameterCount(2)
        .typeParameter(0, "T")
        .typeParameter(1, "S")
        .stateCount(3)
        .end(2)
        .stateTypeParameterCount(0, 1)
        .stateTypeParameterCount(1, 1)
        .stateTypeParameterCount(2, 2)
        .stateTypeParameter(0, 0, "T")
        .stateTypeParameter(1, 0, "T")
        .stateTypeParameter(2, 0, "T")
        .stateTypeParameter(2, 1, "S")
        .stateTypeReferenceCount(0, 0)
        .stateTypeReferenceCount(1, 0)
        .stateTypeReferenceCount(2, 1)
        .stateTypeReference(2, 0, "Qux<T,S>")
        .transitionCount(0, 1)
        .transitionCount(1, 1)
        .transitionCount(2, 0)
        .transitionDestination(0, 0, 1)
        .transitionDestination(1, 0, 2)
        .transitionTypeParameterCount(0, 0, 0)
        .transitionTypeParameterCount(1, 0, 1)
        .transitionTypeParameter(1, 0, 0, "S")
        .transitionLabelRangeCount(0, 0, 1)
        .transitionLabelRangeCount(1, 0, 1);
  }

  @Test
  void test12() {
    test("Foo<T;S> { Qux bar(T t) baz(S s)*; }")
        .name("Foo")
        .typeParameterCount(2)
        .typeParameter(0, "T")
        .typeParameter(1, "S")
        .stateCount(3)
        .end(1, 2)
        .stateTypeParameterCount(0, 1)
        .stateTypeParameterCount(1, 1)
        .stateTypeParameterCount(2, 2)
        .stateTypeParameter(0, 0, "T")
        .stateTypeParameter(1, 0, "T")
        .stateTypeParameter(2, 0, "T")
        .stateTypeParameter(2, 1, "S")
        .stateTypeReferenceCount(0, 0)
        .stateTypeReferenceCount(1, 1)
        .stateTypeReferenceCount(2, 1)
        .stateTypeReference(1, 0, "Qux")
        .stateTypeReference(2, 0, "Qux")
        .transitionCount(0, 1)
        .transitionCount(1, 1)
        .transitionCount(2, 1)
        .transitionDestination(0, 0, 1)
        .transitionDestination(1, 0, 2)
        .transitionDestination(2, 0, 2)
        .transitionTypeParameterCount(0, 0, 0)
        .transitionTypeParameterCount(1, 0, 1)
        .transitionTypeParameterCount(2, 0, 0)
        .transitionTypeParameter(1, 0, 0, "S")
        .transitionLabelRangeCount(0, 0, 1)
        .transitionLabelRangeCount(1, 0, 1)
        .transitionLabelRangeCount(2, 0, 1);
  }

  @SuppressWarnings("AssertBetweenInconvertibleTypes")
  @Test
  void testForCoverage() {
    Diagram diagram = compile("Foo { void foo(); }");
    Label label = diagram.states().get(0).transitions().get(0).label();
    assertThat(label).isNotEqualTo(diagram);
  }

  private DiagramTester test(String text) {
    return new DiagramTester(compile(text));
  }

  private Diagram compile(String text) {
    Grammar grammar = parse(text);
    Diagram diagram = grammar.diagram(new HashMap<>());
    diagram.compile();
    return diagram;
  }

  private Grammar parse(String text) {
    InputStream stream = new ByteArrayInputStream(text.getBytes());
    Parser parser = new Parser(stream);
    try {
      return (Grammar) parser.parse(AgParser::classDeclaration);
    } catch (RecognitionException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
