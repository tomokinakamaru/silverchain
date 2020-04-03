package parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import silverchain.grammar.ASTNode;
import silverchain.parser.ParseException;
import silverchain.parser.Parser;

final class ParserTests {

  @Test
  void testQualifiedName() {
    test(Parser::qualifiedName, "foo");
    test(Parser::qualifiedName, "foo.bar");
    test(Parser::qualifiedName, "foo.bar.baz");
  }

  @Test
  void testTypeParameter() {
    test(Parser::typeParameter, "T");
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
  }

  @Test
  void testGrammars() {
    test(Parser::grammars, "Foo:");
    test(Parser::grammars, "Foo: Bar:");
  }

  private void test(RuleSelector<Parser, ?> selector, String text) {
    test(selector, text, text);
  }

  private void test(RuleSelector<Parser, ?> selector, String text, String expected) {
    InputStream stream = new ByteArrayInputStream(text.getBytes());
    Parser parser = new Parser(stream);
    try {
      ASTNode result = selector.apply(parser);
      assert result.toString().equals(expected);
      assert result.range().begin().line() == result.range().end().line();
      assert result.range().end().column() - result.range().begin().column() + 1 == text.length();
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
