package parser;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import silverchain.grammar.Grammar;
import silverchain.grammar.TypeParameter;
import silverchain.grammar.TypeParameterList;
import silverchain.parser.ParseException;
import silverchain.parser.Parser;

final class ASTTests {

  @SuppressWarnings({"ConstantConditions", "EqualsBetweenInconvertibleTypes", "EqualsWithItself"})
  @Test
  void testEquals() {
    TypeParameter p1 = new TypeParameter("T", null);
    TypeParameter p2 = new TypeParameter("T", null);
    TypeParameterList ps = new TypeParameterList(p1, null);
    assert p1.equals(p2);
    assert p1.equals(p1);
    assert !p1.equals(null);
    assert !p1.equals(ps);
  }

  @Test
  void testHashCode() {
    TypeParameter p1 = new TypeParameter("T", null);
    TypeParameter p2 = new TypeParameter("T", null);
    assert p1.hashCode() == p2.hashCode();
  }

  @Test
  void testCompareTo() {
    TypeParameter p1 = new TypeParameter("S", null);
    TypeParameter p2 = new TypeParameter("T", null);
    assert p1.compareTo(p2) < 0;
  }

  @Test
  void testAccept() throws ParseException {
    String text = "Foo[T;S]: foo(T t) bar(Bar[S] b)*;";

    List<String> list =
        Arrays.asList(
            "QualifiedName",
            "TypeParameter",
            "TypeParameterList",
            "TypeParameter",
            "TypeParameterList",
            "TypeParameters",
            "Type",
            "QualifiedName",
            "TypeReference",
            "MethodParameter",
            "MethodParameters",
            "Method",
            "RuleElement",
            "RuleFactor",
            "QualifiedName",
            "QualifiedName",
            "TypeReference",
            "TypeArgument",
            "TypeArguments",
            "TypeReference",
            "MethodParameter",
            "MethodParameters",
            "Method",
            "RuleElement",
            "RepeatOperator",
            "RuleFactor",
            "RuleTerm",
            "RuleTerm",
            "RuleExpression",
            "Rule",
            "Rules",
            "Grammar");

    Grammar grammar = new Parser(new ByteArrayInputStream(text.getBytes())).grammar();
    TestVisitor visitor = new TestVisitor();
    grammar.accept(visitor);
    assert visitor.list.equals(list);
  }
}
