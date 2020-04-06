package parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import silverchain.parser.ASTNode;
import silverchain.parser.DuplicateDeclaration;
import silverchain.parser.ParseException;
import silverchain.parser.Parser;

final class Tests {

  @Test
  void testQualifiedName() {
    test(Parser::qualifiedName, "foo");
    test(Parser::qualifiedName, "foo.bar");
    test(Parser::qualifiedName, "foo.bar.baz");
  }

  @Test
  void testTypeParameter() {
    test(Parser::typeParameter, "T");

    ASTNode node = parse(Parser::typeParameter, "T");
    assert node.typeParameters().size() == 1;
    assert node.typeParameters().get(0).name().equals("T");
  }

  @Test
  void testTypeParameterBound() {
    test(Parser::typeParameterBound, "<: Foo");
    test(Parser::typeParameterBound, ":> Foo");
  }

  @Test
  void testTypeParameterList() {
    test(Parser::typeParameterList, "T");
    test(Parser::typeParameterList, "T,S");
  }

  @Test
  void testTypeParameters() {
    test(Parser::typeParameters, "T;S");
    test(Parser::typeParameters, "T");
    test(Parser::typeParameters, ";T");
  }

  @Test
  void testType() {
    test(Parser::type, "Foo");
    test(Parser::type, "Foo[T;S]");
    test(Parser::type, "Foo[T,S]");
    test(Parser::type, "Foo[T,S;U]");
    test(Parser::type, "Foo[T;S,U]");
  }

  @Test
  void testTypeReferences() {
    test(Parser::typeReferences, "T");
    test(Parser::typeReferences, "T,S");
  }

  @Test
  void testTypeReference() {
    test(Parser::typeReference, "Foo");
    test(Parser::typeReference, "foo.Bar[T]");
  }

  @Test
  void testMethodParameter() {
    test(Parser::methodParameter, "Foo foo");
  }

  @Test
  void testMethodParameters() {
    test(Parser::methodParameters, "Foo foo");
    test(Parser::methodParameters, "Foo foo,Bar bar");
  }

  @Test
  void testMethod() {
    test(Parser::method, "foo()");
    test(Parser::method, "foo(Bar bar)");
  }

  @Test
  void testRuleElement() {
    test(Parser::ruleElement, "foo()");
    test(Parser::ruleElement, "(foo()|bar())");
  }

  @Test
  void testRepeatOperator() {
    test(Parser::repeatOperator, "*", "{0}");
    test(Parser::repeatOperator, "+", "{1}");
    test(Parser::repeatOperator, "?", "{0,1}");
    test(Parser::repeatOperator, "{1}");
    test(Parser::repeatOperator, "{0,1}");
    test(Parser::repeatOperator, "{2}");
    test(Parser::repeatOperator, "{0,2}");
    test(Parser::repeatOperator, "{1,2}");
    test(Parser::repeatOperator, "{2,3}");
  }

  @Test
  void testRuleFactor() {
    test(Parser::ruleFactor, "foo()");
    test(Parser::ruleFactor, "foo()*", "foo(){0}");
  }

  @Test
  void testRuleTerm() {
    test(Parser::ruleTerm, "foo()");
    test(Parser::ruleTerm, "foo() bar()");
  }

  @Test
  void testRuleExpression() {
    test(Parser::ruleExpression, "foo()");
    test(Parser::ruleExpression, "foo()|bar()|baz()");
  }

  @Test
  void testRule() {
    test(Parser::rule, "foo();");
    test(Parser::rule, "foo() Foo;");
  }

  @Test
  void testRules() {
    test(Parser::rules, "foo() Foo; bar() Bar;");
  }

  @Test
  void testGrammar() {
    test(Parser::grammar, "Foo:");
    test(Parser::grammar, "Foo: foo() Foo;");

    ASTNode node1 = parse(Parser::grammar, "Foo[T, T]:");
    assertThrows(DuplicateDeclaration.class, node1::validate);

    ASTNode node2 = parse(Parser::grammar, "Foo[T, S]:");
    assertDoesNotThrow(node2::validate);
  }

  @Test
  void testGrammars() {
    test(Parser::grammars, "Foo:");
    test(Parser::grammars, "Foo: Bar:");
  }

  @Test
  void testRange() {
    ASTNode node1 = parse(Parser::grammar, "Foo:");
    ASTNode node2 = parse(Parser::grammar, "Bar:");
    ASTNode node3 = parse(Parser::grammar, "Baz[T]:");
    ASTNode node4 = parse(Parser::grammar, "\nQux:");

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
    ASTNode node1 = parse(Parser::grammar, "Foo:");
    ASTNode node2 = parse(Parser::grammar, "Bar:");

    assert node1.hashCode() != node2.hashCode();
    assert !node1.equals(node2);
    assert node1.compareTo(node2) > 0;

    assert node1.equals(node1);
    assert !node1.equals(null);
    assert !node1.equals(node1.range());

    assert node1.range().equals(node1.range());
    assert !node1.range().equals(null);
    assert !node1.range().equals(node1);
    assert node1.range().toString().equals("L1C1-L1C4");

    assert node1.range().begin().equals(node1.range().begin());
    assert !node1.range().begin().equals(null);
    assert !node1.range().begin().equals(node1);
  }

  private void test(RuleSelector<Parser, ?> selector, String text) {
    test(selector, text, text);
  }

  private void test(RuleSelector<Parser, ?> selector, String text, String expected) {
    ASTNode result = parse(selector, text);
    assert result.toString().equals(expected);
    assert result.range().begin().line() == result.range().end().line();
    assert result.range().end().column() - result.range().begin().column() + 1 == text.length();
  }

  protected ASTNode parse(RuleSelector<Parser, ?> selector, String text) {
    InputStream stream = new ByteArrayInputStream(text.getBytes());
    Parser parser = new Parser(stream);
    try {
      return selector.apply(parser);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
