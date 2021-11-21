package parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;
import silverchain.parser.ASTNode;
import silverchain.parser.AgParser;
import silverchain.parser.DuplicateDeclaration;
import silverchain.parser.adapter.Parser;

final class Tests {

  @Test
  void testQualifiedName() {
    test(AgParser::qualifiedName, "foo");
    test(AgParser::qualifiedName, "foo.bar");
    test(AgParser::qualifiedName, "foo.bar.baz");
  }

  @Test
  void testTypeParameter() {
    test(AgParser::typeParameter, "T");

    ASTNode node = parse(AgParser::typeParameter, "T");
    assert node.typeParameters().size() == 1;
    assert node.typeParameters().get(0).name().equals("T");
  }

  @Test
  void testTypeParameterBound() {
    test(AgParser::wildcardBound, "extends Foo");
    test(AgParser::wildcardBound, "super Foo");
  }

  @Test
  void testTypeParameterList() {
    test(AgParser::typeParameterList, "T");
    test(AgParser::typeParameterList, "T,S");
  }

  @Test
  void testTypeParameters() {
    test(AgParser::classTypeParameterDeclarations, "<T;S>");
    test(AgParser::classTypeParameterDeclarations, "<T>");
    test(AgParser::classTypeParameterDeclarations, "<;T>");
  }

  @Test
  void testType() {
    test(AgParser::classDeclarationHead, "Foo");
    test(AgParser::classDeclarationHead, "Foo<T;S>");
    test(AgParser::classDeclarationHead, "Foo<T,S>");
    test(AgParser::classDeclarationHead, "Foo<T,S;U>");
    test(AgParser::classDeclarationHead, "Foo<T;S,U>");
  }

  @Test
  void testTypeArgument() {
    test(AgParser::typeArgument, "T");
    test(AgParser::wildcard, "?");
    test(AgParser::wildcard, "? extends T");
    test(AgParser::wildcard, "? super T");
  }

  @Test
  void testTypeArguments() {
    test(AgParser::typeArgumentList, "T");
    test(AgParser::typeArgumentList, "T,S");
  }

  @Test
  void testTypeReference() {
    test(AgParser::typeReference, "Foo");
    test(AgParser::typeReference, "foo.Bar<T>");
    test(AgParser::typeReference, "Foo[]");
    test(AgParser::typeReference, "Foo<T>[]");
  }

  @Test
  void testFormalParameter() {
    test(AgParser::formalParameter, "Foo foo");
    test(AgParser::formalParameter, "Foo... foo");
  }

  @Test
  void testFormalParameters() {
    test(AgParser::formalParameterList, "Foo foo");
    test(AgParser::formalParameterList, "Foo foo,Bar bar");
  }

  @Test
  void testMethod() {
    test(AgParser::method, "foo()");
    test(AgParser::method, "foo(Bar bar)");
    test(AgParser::method, "foo() throws Foo");
  }

  @Test
  void testRuleElement() {
    test(AgParser::ruleElement, "foo()");
    test(AgParser::ruleElement, "(foo())");
    test(AgParser::ruleElement, "(foo()|bar())");
    test(AgParser::ruleElement, "{foo()}", "{foo()}");
    test(AgParser::ruleElement, "{foo(),bar()}");
  }

  @Test
  void testRepeatOperator() {
    test(AgParser::repeatOperator, "*", "[0,]");
    test(AgParser::repeatOperator, "+", "[1,]");
    test(AgParser::repeatOperator, "?", "[0,1]");
    test(AgParser::repeatOperator, "[1]", "[1,1]");
    test(AgParser::repeatOperator, "[2]", "[2,2]");
    test(AgParser::repeatOperator, "[1,]", "[1,]");
    test(AgParser::repeatOperator, "[0,1]");
    test(AgParser::repeatOperator, "[0,2]");
    test(AgParser::repeatOperator, "[1,2]");
    test(AgParser::repeatOperator, "[2,3]");
  }

  @Test
  void testRuleFactor() {
    test(AgParser::ruleFactor, "foo()");
    test(AgParser::ruleFactor, "foo()*", "foo()[0,]");
  }

  @Test
  void testRuleTerm() {
    test(AgParser::ruleTerm, "foo()");
    test(AgParser::ruleTerm, "foo() bar()");
  }

  @Test
  void testRuleExpression() {
    test(AgParser::ruleExpression, "foo()");
    test(AgParser::ruleExpression, "foo()|bar()|baz()");
  }

  @Test
  void testRule() {
    test(AgParser::ruleStatement, "void foo();");
    test(AgParser::ruleStatements, "Foo foo();");
  }

  @Test
  void testRules() {
    test(AgParser::ruleStatements, "Foo foo(); Bar bar();");
  }

  @Test
  void testGrammar() {
    test(AgParser::classDeclaration, "Foo {}");
    test(AgParser::classDeclaration, "Foo { Foo foo(); }");

    ASTNode node1 = parse(AgParser::classDeclaration, "Foo<T, T> {}");
    assertThrows(DuplicateDeclaration.class, node1::validate);

    ASTNode node2 = parse(AgParser::classDeclaration, "Foo<T, S> {}");
    assertDoesNotThrow(node2::validate);
  }

  @Test
  void testRange() {
    ASTNode node1 = parse(AgParser::classDeclaration, "Foo {}");
    ASTNode node2 = parse(AgParser::classDeclaration, "Bar {}");
    ASTNode node3 = parse(AgParser::classDeclaration, "Baz<T> {}");
    ASTNode node4 = parse(AgParser::classDeclaration, "\nQux {}");

    assert node1.range().hashCode() == node2.range().hashCode();

    assert node1.range().equals(node2.range());
    assert !node1.range().equals(node3.range());
    assert !node1.range().equals(node4.range());

    assert node1.range().compareTo(node2.range()) == 0;
    assert node1.range().compareTo(node3.range()) < 0;
    assert node3.range().compareTo(node1.range()) > 0;
    assert node1.range().compareTo(node4.range()) < 0;
    assert node4.range().compareTo(node1.range()) > 0;
  }

  @Test
  @SuppressWarnings({"EqualsWithItself", "ConstantConditions", "EqualsBetweenInconvertibleTypes"})
  void testForCoverage() {
    ASTNode node1 = parse(AgParser::classDeclaration, "Foo {}");
    ASTNode node2 = parse(AgParser::classDeclaration, "Bar {}");

    assert node1.hashCode() != node2.hashCode();
    assert !node1.equals(node2);
    assert node1.compareTo(node2) > 0;

    assert node1.equals(node1);
    assert !node1.equals(null);
    assert !node1.equals(node1.range());

    assert node1.range().equals(node1.range());
    assert !node1.range().equals(null);
    assert !node1.range().equals(node1);
    assert node1.range().toString().equals("L1C1-L1C6");

    assert node1.range().begin().equals(node1.range().begin());
    assert !node1.range().begin().equals(null);
    assert !node1.range().begin().equals(node1);
  }

  private void test(Function<AgParser, ParseTree> selector, String text) {
    test(selector, text, text);
  }

  private void test(Function<AgParser, ParseTree> selector, String text, String expected) {
    ASTNode result = parse(selector, text);
    assert result.toString().equals(expected);
    assert result.range().begin().line() == result.range().end().line();
    assert result.range().end().column() - result.range().begin().column() + 1 == text.length();
  }

  protected ASTNode parse(Function<AgParser, ParseTree> selector, String text) {
    InputStream stream = new ByteArrayInputStream(text.getBytes());
    Parser parser = new Parser(stream);
    try {
      return parser.parse(selector);
    } catch (RecognitionException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
