package internal.frontend.parser;

import static internal.utility.Functions.parser;
import static org.assertj.core.api.Assertions.assertThat;

import internal.utility.ParserSelector;
import java.util.function.Function;
import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.internal.frontend.antlr.AgParser;

class AntlrParserTest {

  private static Arguments[] data() {
    // @formatter:off
    return new Arguments[]{
        args(
            AgParser::input,
            "",
            "(input <EOF>)"),
        args(
            AgParser::importDecl,
            "import foo;",
            "(importDecl import (name foo) ;)"),
        args(
            AgParser::fragmentDecl,
            "$FOO = foo();",
            "(fragmentDecl $FOO = (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;)"),
        args(
            AgParser::typeDecl,
            "Foo { Foo foo(); }",
            "(typeDecl (name Foo) { (chainStmts (chainStmt (returnType (typeRef (name Foo))) " +
                "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;)) })"),
        args(
            AgParser::typeDecl,
            "Foo<T> { Foo foo(); }",
            "(typeDecl (name Foo) < (externalParams (typeParams (typeParam T))) > { (chainStmts (chainStmt (returnType (typeRef (name Foo))) " +
                "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;)) })"),
        args(
            AgParser::typeDecl,
            "Foo<T;S> { Foo foo(); }",
            "(typeDecl (name Foo) < (externalParams (typeParams (typeParam T))) (internalParams ; (typeParams (typeParam S))) > { (chainStmts (chainStmt " +
                "(returnType (typeRef (name Foo))) (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;)) })"),
        args(
            AgParser::typeDecl,
            "Foo<;T> { Foo foo(); }",
            "(typeDecl (name Foo) < (internalParams ; (typeParams (typeParam T))) > { (chainStmts (chainStmt " +
                "(returnType (typeRef (name Foo))) (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;)) })"),
        args(
            AgParser::chainStmts,
            "Foo foo(); Foo foo();",
            "(chainStmts " +
                "(chainStmt (returnType (typeRef (name Foo))) (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;) " +
                "(chainStmt (returnType (typeRef (name Foo))) (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;))"),
        args(
            AgParser::chainStmt,
            "Foo foo();",
            "(chainStmt (returnType (typeRef (name Foo))) (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;)"),
        args(
            AgParser::chainExpr,
            "foo() | foo()",
            "(chainExpr (chainTerm (chainFact (chainElem (method foo ( ))))) | (chainTerm (chainFact (chainElem (method foo ( ))))))"),
        args(
            AgParser::chainTerm,
            "foo() foo()",
            "(chainTerm (chainFact (chainElem (method foo ( )))) (chainFact (chainElem (method foo ( )))))"),
        args(
            AgParser::chainFact,
            "foo()*",
            "(chainFact (chainElem (method foo ( ))) *)"),
        args(
            AgParser::chainFact,
            "foo()+",
            "(chainFact (chainElem (method foo ( ))) +)"),
        args(
            AgParser::chainFact,
            "foo()?",
            "(chainFact (chainElem (method foo ( ))) ?)"),
        args(
            AgParser::returnType,
            "Foo",
            "(returnType (typeRef (name Foo)))"),
        args(
            AgParser::repeatN,
            "[1]",
            "(repeatN [ 1 ])"),
        args(
            AgParser::repeatNX,
            "[1,]",
            "(repeatNX [ 1 , ])"),
        args(
            AgParser::repeatNM,
            "[1,2]",
            "(repeatNM [ 1 , 2 ])"),
        args(
            AgParser::permutation,
            "{ foo() }",
            "(permutation { (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) })"),
        args(
            AgParser::permutation,
            "{ foo(), }",
            "(permutation { (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) , })"),
        args(
            AgParser::permutation,
            "{ foo(), foo() }",
            "(permutation { " +
                "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) , " +
                "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) })"),
        args(
            AgParser::permutation,
            "{ foo(), foo(), }",
            "(permutation { " +
                "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) , " +
                "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) , })"),
        args(
            AgParser::method,
            "foo<T>()",
            "(method foo < (typeParams (typeParam T)) > ( ))"),
        args(
            AgParser::method,
            "foo(Foo foo)",
            "(method foo ( (params (param (typeRef (name Foo)) foo)) ))"),
        args(
            AgParser::method,
            "foo() throws Foo",
            "(method foo ( ) (exceptions throws (typeRef (name Foo))))"),
        args(
            AgParser::exceptions,
            "throws Foo, Foo",
            "(exceptions throws (typeRef (name Foo)) , (typeRef (name Foo)))"),
        args(
            AgParser::params,
            "Foo foo, Foo foo",
            "(params (param (typeRef (name Foo)) foo) , (param (typeRef (name Foo)) foo))"),
        args(
            AgParser::param,
            "Foo... foo",
            "(param (typeRef (name Foo)) ... foo)"),
        args(
            AgParser::fragmentRef,
            "$FOO",
            "(fragmentRef $FOO)"),
        args(
            AgParser::typeRef,
            "Foo<Foo>",
            "(typeRef (name Foo) (typeArgs < (typeArg (typeRef (name Foo))) >))"),
        args(
            AgParser::typeRef,
            "Foo[]",
            "(typeRef (name Foo) [])"),
        args(
            AgParser::typeRef,
            "Foo[][]",
            "(typeRef (name Foo) [] [])"),
        args(
            AgParser::typeArgs,
            "<T,S>",
            "(typeArgs < (typeArg (typeRef (name T))) , (typeArg (typeRef (name S))) >)"),
        args(
            AgParser::typeArg,
            "Foo",
            "(typeArg (typeRef (name Foo)))"),
        args(
            AgParser::typeArg,
            "?",
            "(typeArg (wildcard ?))"),
        args(
            AgParser::wildcard,
            "? super Foo",
            "(wildcard ? super (typeRef (name Foo)))"),
        args(
            AgParser::wildcard,
            "? extends Foo",
            "(wildcard ? extends (typeRef (name Foo)))"),
        args(
            AgParser::typeParams,
            "T, S",
            "(typeParams (typeParam T) , (typeParam S))"),
        args(
            AgParser::typeParam,
            "T extends Foo",
            "(typeParam T (bounds extends (typeRef (name Foo))))"),
        args(
            AgParser::bounds,
            "extends Foo & Foo",
            "(bounds extends (typeRef (name Foo)) & (typeRef (name Foo)))"),
        args(
            AgParser::name,
            "foo",
            "(name foo)"),
        args(
            AgParser::name,
            "foo.foo",
            "(name (qualifier foo .) foo)"),
        args(
            AgParser::qualifier,
            "foo.foo.",
            "(qualifier foo . foo .)")
    };
    // @formatter:on
  }

  @ParameterizedTest(name = "[{index}] \"{1}\" ->\"{2}\"")
  @MethodSource("data")
  void test(Function<AgParser, ParserRuleContext> selector, String text, String expected) {
    AgParser parser = parser(text);
    ParserRuleContext ctx = selector.apply(parser);
    assertThat(ctx.toStringTree(parser)).isEqualTo(expected);
  }

  private static Arguments args(ParserSelector selector, String text, String expected) {
    return Arguments.of(selector, text, expected);
  }
}
