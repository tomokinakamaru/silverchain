import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static utility.Functions.parse;
import static utility.Functions.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.ag.builder.AgParser;
import silverchain.ag.builder.SyntaxError;
import utility.ParserSelector;

public class AgParserTest {

  private static Arguments[] data() {
    // @formatter:off
    return new Arguments[] {
        args(AgParser::input,
            "",
            "(input <EOF>)"),
        args(AgParser::aliasDecl,
            "import Foo;",
            "(aliasDecl import (name Foo) ;)"),
        args(AgParser::fragmentDecl,
            "$FOO = foo();",
            "(fragmentDecl $FOO = (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;)"),
        args(AgParser::typeDecl,
            "Foo<T> { Foo foo(); }",
            "(typeDecl (name Foo) < (externalParams (typeParams (typeParam T))) > (typeDeclBody { "
                + "(chainStmt "
                + "(returnType (typeRef (name Foo))) "
                + "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;) }))"),
        args(AgParser::typeDecl,
            "Foo<;T> { Foo foo(); }",
            "(typeDecl (name Foo) < (internalParams ; (typeParams (typeParam T))) > (typeDeclBody { "
                + "(chainStmt "
                + "(returnType (typeRef (name Foo))) "
                + "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;) }))"),
        args(AgParser::typeDecl,
            "Foo<T;S> { Foo foo(); }",
            "(typeDecl (name Foo) "
                + "< (externalParams (typeParams (typeParam T))) (internalParams ; (typeParams (typeParam S))) > "
                + "(typeDeclBody { (chainStmt "
                + "(returnType (typeRef (name Foo))) "
                + "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;) }))"),
        args(AgParser::externalParams,
            "T",
            "(externalParams (typeParams (typeParam T)))"),
        args(AgParser::internalParams,
            ";T",
            "(internalParams ; (typeParams (typeParam T)))"),
        args(AgParser::typeDeclBody,
            "{ Foo foo(); Foo foo(); }",
            "(typeDeclBody { "
                + "(chainStmt (returnType (typeRef (name Foo))) (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;) "
                + "(chainStmt (returnType (typeRef (name Foo))) (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;) })"),
        args(AgParser::chainStmt,
            "Foo foo();",
            "(chainStmt (returnType (typeRef (name Foo))) (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;)"),
        args(AgParser::chainTerm,
            "foo()",
            "(chainTerm (chainFact (chainElem (method foo ( )))))"),
        args(AgParser::chainExpr,
            "foo() | foo()",
            "(chainExpr (chainTerm (chainFact (chainElem (method foo ( ))))) | (chainTerm (chainFact (chainElem (method foo ( ))))))"),
        args(AgParser::chainTerm,
            "foo()",
            "(chainTerm (chainFact (chainElem (method foo ( )))))"),
        args(AgParser::chainTerm,
            "foo() foo()",
            "(chainTerm (chainFact (chainElem (method foo ( )))) (chainFact (chainElem (method foo ( )))))"),
        args(AgParser::chainFact,
            "foo()",
            "(chainFact (chainElem (method foo ( ))))"),
        args(AgParser::chainFact,
            "foo()*",
            "(chainFact (chainElem (method foo ( ))) *)"),
        args(AgParser::chainFact,
            "foo()?",
            "(chainFact (chainElem (method foo ( ))) ?)"),
        args(AgParser::chainFact,
            "foo()+",
            "(chainFact (chainElem (method foo ( ))) +)"),
        args(AgParser::chainFact,
            "foo()[1]",
            "(chainFact (chainElem (method foo ( ))) (repeatN [ 1 ]))"),
        args(AgParser::chainFact,
            "foo()[1,]",
            "(chainFact (chainElem (method foo ( ))) (repeatNX [ 1 , ]))"),
        args(AgParser::chainFact,
            "foo()[1,1]",
            "(chainFact (chainElem (method foo ( ))) (repeatNM [ 1 , 1 ]))"),
        args(AgParser::chainElem,
            "( foo() )",
            "(chainElem ( (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ))"),
        args(AgParser::chainElem,
            "$FOO",
            "(chainElem (fragmentRef $FOO))"),
        args(AgParser::chainElem,
            "{ foo() }",
            "(chainElem (permutation { (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) }))"),
        args(AgParser::chainElem,
            "foo()",
            "(chainElem (method foo ( )))"),
        args(AgParser::returnType,
            "Foo",
            "(returnType (typeRef (name Foo)))"),
        args(AgParser::repeatN,
            "[1]",
            "(repeatN [ 1 ])"),
        args(AgParser::repeatNX,
            "[1,]",
            "(repeatNX [ 1 , ])"),
        args(AgParser::repeatNM,
            "[1,1]",
            "(repeatNM [ 1 , 1 ])"),
        args(AgParser::permutation,
            "{ foo() }",
            "(permutation { (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) })"),
        args(AgParser::permutation,
            "{ foo(), }",
            "(permutation { (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) , })"),
        args(AgParser::permutation,
            "{ foo(), foo() }",
            "(permutation { "
                + "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) , "
                + "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) })"),
        args(AgParser::method,
            "foo()",
            "(method foo ( ))"),
        args(AgParser::method,
            "foo<Foo>()",
            "(method foo < (typeParams (typeParam Foo)) > ( ))"),
        args(AgParser::method,
            "foo(Foo foo)",
            "(method foo ( (params (param (typeRef (name Foo)) foo)) ))"),
        args(AgParser::method,
            "foo() throws Foo",
            "(method foo ( ) (exceptions throws (typeRef (name Foo))))"),
        args(AgParser::exceptions,
            "throws Foo",
            "(exceptions throws (typeRef (name Foo)))"),
        args(AgParser::exceptions,
            "throws Foo, Foo",
            "(exceptions throws (typeRef (name Foo)) , (typeRef (name Foo)))"),
        args(AgParser::param,
            "Foo foo",
            "(param (typeRef (name Foo)) foo)"),
        args(AgParser::params,
            "Foo foo, Foo foo",
            "(params (param (typeRef (name Foo)) foo) , (param (typeRef (name Foo)) foo))"),
        args(AgParser::param,
            "Foo foo",
            "(param (typeRef (name Foo)) foo)"),
        args(AgParser::param,
            "Foo... foo",
            "(param (typeRef (name Foo)) ... foo)"),
        args(AgParser::fragmentRef,
            "$FOO",
            "(fragmentRef $FOO)"),
        args(AgParser::typeRef,
            "foo",
            "(typeRef (name foo))"),
        args(AgParser::typeRef,
            "foo<?>",
            "(typeRef (name foo) (typeArgs < (typeArg (wildcard ?)) >))"),
        args(AgParser::typeRef,
            "foo[]",
            "(typeRef (name foo) [])"),
        args(AgParser::typeRef,
            "foo[][]",
            "(typeRef (name foo) [] [])"),
        args(AgParser::typeArgs,
            "<?>",
            "(typeArgs < (typeArg (wildcard ?)) >)"),
        args(AgParser::typeArgs,
            "<?, ?>",
            "(typeArgs < (typeArg (wildcard ?)) , (typeArg (wildcard ?)) >)"),
        args(AgParser::typeArg,
            "Foo",
            "(typeArg (typeRef (name Foo)))"),
        args(AgParser::typeArg,
            "?",
            "(typeArg (wildcard ?))"),
        args(AgParser::wildcard,
            "?",
            "(wildcard ?)"),
        args(AgParser::wildcard,
            "? super Foo",
            "(wildcard ? super (typeRef (name Foo)))"),
        args(AgParser::wildcard,
            "? extends Foo",
            "(wildcard ? extends (typeRef (name Foo)))"),
        args(AgParser::typeParams,
            "T",
            "(typeParams (typeParam T))"),
        args(AgParser::typeParams,
            "T, S",
            "(typeParams (typeParam T) , (typeParam S))"),
        args(AgParser::typeParam,
            "T",
            "(typeParam T)"),
        args(AgParser::typeParam,
            "T extends Foo",
            "(typeParam T (bounds extends (typeRef (name Foo))))"),
        args(AgParser::bounds,
            "extends Foo",
            "(bounds extends (typeRef (name Foo)))"),
        args(AgParser::bounds,
            "extends Foo & Foo",
            "(bounds extends (typeRef (name Foo)) & (typeRef (name Foo)))"),
        args(AgParser::name,
            "foo.foo",
            "(name (qualifier foo .) foo)"),
        args(AgParser::name,
            "foo",
            "(name foo)"),
        args(AgParser::qualifier,
            "foo.",
            "(qualifier foo .)")
    };
    // @formatter:on
  }

  private static Arguments args(ParserSelector selector, String text, String expected) {
    return Arguments.of(selector, text, expected);
  }

  @ParameterizedTest(name = "[{index}] \"{1}\" -> \"{2}\"")
  @MethodSource("data")
  void test(ParserSelector selector, String text, String expected) {
    AgParser parser = parser(text);
    ParserRuleContext ctx = selector.apply(parser);
    assertThat(ctx.toStringTree(parser)).isEqualTo(expected);
  }

  private static Arguments[] errorData() {
    return new Arguments[] {
      Arguments.of("!", "Syntax error: Token recognition error at: '!' (L1C1)")
    };
  }

  @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{1}\"")
  @MethodSource("errorData")
  void errorTest(String text, String message) {
    assertThatThrownBy(() -> parse(text))
        .isExactlyInstanceOf(SyntaxError.class)
        .hasMessage(message);
  }
}
