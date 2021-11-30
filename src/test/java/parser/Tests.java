package parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.parser.ASTNode;
import silverchain.parser.AgParser;
import silverchain.parser.DuplicateDeclaration;
import silverchain.parser.Location;
import silverchain.parser.Range;
import silverchain.parser.adapter.Parser;

final class Tests {

  static Arguments[] bulkTestData() {
    return new Arguments[] {
      args(AgParser::qualifiedName, "foo"),
      args(AgParser::qualifiedName, "foo.bar"),
      args(AgParser::qualifiedName, "foo.bar.baz"),
      args(AgParser::wildcardBound, "extends Foo"),
      args(AgParser::wildcardBound, "super Foo"),
      args(AgParser::typeParameterList, "T"),
      args(AgParser::typeParameterList, "T,S"),
      args(AgParser::classTypeParameterDeclarations, "<T;S>"),
      args(AgParser::classTypeParameterDeclarations, "<T>"),
      args(AgParser::classTypeParameterDeclarations, "<;T>"),
      args(AgParser::classDeclarationHead, "Foo"),
      args(AgParser::classDeclarationHead, "Foo<T;S>"),
      args(AgParser::classDeclarationHead, "Foo<T,S>"),
      args(AgParser::classDeclarationHead, "Foo<T,S;U>"),
      args(AgParser::classDeclarationHead, "Foo<T;S,U>"),
      args(AgParser::typeArgument, "T"),
      args(AgParser::wildcard, "?"),
      args(AgParser::wildcard, "? extends T"),
      args(AgParser::wildcard, "? super T"),
      args(AgParser::typeArgumentList, "T"),
      args(AgParser::typeArgumentList, "T,S"),
      args(AgParser::typeReference, "Foo"),
      args(AgParser::typeReference, "foo.Bar<T>"),
      args(AgParser::typeReference, "Foo[]"),
      args(AgParser::typeReference, "Foo<T>[]"),
      args(AgParser::formalParameter, "Foo foo"),
      args(AgParser::formalParameter, "Foo... foo"),
      args(AgParser::formalParameterList, "Foo foo"),
      args(AgParser::formalParameterList, "Foo foo,Bar bar"),
      args(AgParser::method, "foo()"),
      args(AgParser::method, "foo(Bar bar)"),
      args(AgParser::method, "foo() throws Foo"),
      args(AgParser::ruleElement, "foo()"),
      args(AgParser::ruleElement, "(foo())"),
      args(AgParser::ruleElement, "(foo()|bar())"),
      args(AgParser::ruleElement, "{foo()}", "{foo()}"),
      args(AgParser::ruleElement, "{foo(),bar()}"),
      args(AgParser::repeatOperator, "*", "[0,]"),
      args(AgParser::repeatOperator, "+", "[1,]"),
      args(AgParser::repeatOperator, "?", "[0,1]"),
      args(AgParser::repeatOperator, "[1]", "[1,1]"),
      args(AgParser::repeatOperator, "[2]", "[2,2]"),
      args(AgParser::repeatOperator, "[1,]", "[1,]"),
      args(AgParser::repeatOperator, "[0,1]"),
      args(AgParser::repeatOperator, "[0,2]"),
      args(AgParser::repeatOperator, "[1,2]"),
      args(AgParser::repeatOperator, "[2,3]"),
      args(AgParser::ruleFactor, "foo()"),
      args(AgParser::ruleFactor, "foo()*", "foo()[0,]"),
      args(AgParser::ruleTerm, "foo()"),
      args(AgParser::ruleTerm, "foo() bar()"),
      args(AgParser::ruleExpression, "foo()"),
      args(AgParser::ruleExpression, "foo()|bar()|baz()"),
      args(AgParser::ruleStatement, "void foo();"),
      args(AgParser::ruleStatements, "Foo foo();"),
      args(AgParser::ruleStatements, "Foo foo(); Bar bar();"),
    };
  }

  static Arguments args(Function<AgParser, ParseTree> selector, String text) {
    return args(selector, text, text);
  }

  static Arguments args(Function<AgParser, ParseTree> selector, String text, String expected) {
    return Arguments.of(selector, text, expected);
  }

  @ParameterizedTest(name = "[{index}] \"{1}\"")
  @MethodSource("bulkTestData")
  void testBulk(Function<AgParser, ParseTree> selector, String text, String expected) {
    test(selector, text, expected);
  }

  @Test
  void testTypeParameter() {
    test(AgParser::typeParameter, "T");

    ASTNode node = parse(AgParser::typeParameter, "T");
    assertThat(node.typeParameters().size()).isSameAs(1);
    assertThat(node.typeParameters().get(0).name()).isEqualTo("T");
  }

  @Test
  void testGrammar() {
    test(AgParser::classDeclaration, "Foo {}");
    test(AgParser::classDeclaration, "Foo { Foo foo(); }");

    ASTNode node1 = parse(AgParser::classDeclaration, "Foo<T, T> {}");
    assertThatThrownBy(node1::validate).isExactlyInstanceOf(DuplicateDeclaration.class);

    ASTNode node2 = parse(AgParser::classDeclaration, "Foo<T, S> {}");
    assertThatCode(node2::validate).doesNotThrowAnyException();
  }

  @Test
  void testRange() {
    ASTNode node1 = parse(AgParser::classDeclaration, "Foo {}");
    ASTNode node2 = parse(AgParser::classDeclaration, "Bar {}");
    ASTNode node3 = parse(AgParser::classDeclaration, "Baz<T> {}");
    ASTNode node4 = parse(AgParser::classDeclaration, "\nQux {}");

    Range range1 = node1.range();
    assertThat(range1)
        .hasSameHashCodeAs(node2.range())
        .isEqualTo(node2.range())
        .isEqualByComparingTo(node2.range())
        .isNotEqualTo(node3.range())
        .isNotEqualTo(node4.range())
        .isLessThan(node3.range())
        .isLessThan(node4.range());

    assertThat(node3.range()).isGreaterThan(range1);

    assertThat(node4.range()).isGreaterThan(range1);
  }

  @Test
  void testForCoverage() {
    ASTNode node1 = parse(AgParser::classDeclaration, "Foo {}");
    ASTNode node2 = parse(AgParser::classDeclaration, "Bar {}");

    assertThat(node1)
        .isNotNull()
        .doesNotHaveSameHashCodeAs(node2)
        .isNotEqualTo(node2)
        .isGreaterThan(node2)
        .isEqualTo(node1)
        .isNotEqualTo(node1.range());

    Range range1 = node1.range();
    assertThat(range1).isNotNull().isEqualTo(range1).isNotEqualTo(node1).hasToString("L1C1-L1C6");

    Location begin1 = range1.begin();
    assertThat(begin1).isNotNull().isEqualTo(begin1).isNotEqualTo(node1);
  }

  private void test(Function<AgParser, ParseTree> selector, String text) {
    test(selector, text, text);
  }

  private void test(Function<AgParser, ParseTree> selector, String text, String expected) {
    ASTNode result = parse(selector, text);
    assertThat(result).hasToString(expected);
    assertThat(result.range().begin().line()).isSameAs(result.range().end().line());
    assertThat(result.range().end().column() - result.range().begin().column() + 1)
        .isSameAs(text.length());
  }

  private ASTNode parse(Function<AgParser, ParseTree> selector, String text) {
    InputStream stream = new ByteArrayInputStream(text.getBytes());
    Parser parser = new Parser(stream);
    try {
      return parser.parse(selector);
    } catch (RecognitionException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
