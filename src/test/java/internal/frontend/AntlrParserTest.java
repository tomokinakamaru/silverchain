package internal.frontend;

import static internal.frontend.utility.Functions.parser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import internal.frontend.utility.ParserSelector;
import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.internal.frontend.builder.AntlrParser;

public class AntlrParserTest {

  private static Arguments[] data() {
    // @formatter:off
    return new Arguments[] {
        args(AntlrParser::input,
            "",
            "(input <EOF>)"),
        args(AntlrParser::fragmentDecl,
            "import Foo;",
            "(fragmentDecl import Foo ;)"),
        args(AntlrParser::fragmentDecl,
            "$FOO = foo();",
            "(fragmentDecl $FOO = (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;)"),
        args(AntlrParser::typeDecl,
            "Foo<T> { Foo foo(); }",
            "(typeDecl (name Foo) < (externalParams (typeParams (typeParam T))) > (typeDeclBody { "
                + "(chainStmt "
                + "(returnType (typeRef (name Foo))) "
                + "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;) }))"),
        args(AntlrParser::typeDecl,
            "Foo<;T> { Foo foo(); }",
            "(typeDecl (name Foo) < (internalParams ; (typeParams (typeParam T))) > (typeDeclBody { "
                + "(chainStmt "
                + "(returnType (typeRef (name Foo))) "
                + "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;) }))"),
        args(AntlrParser::typeDecl,
            "Foo<T;S> { Foo foo(); }",
            "(typeDecl (name Foo) "
                + "< (externalParams (typeParams (typeParam T))) (internalParams ; (typeParams (typeParam S))) > "
                + "(typeDeclBody { (chainStmt "
                + "(returnType (typeRef (name Foo))) "
                + "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;) }))"),
        args(AntlrParser::externalParams,
            "T",
            "(externalParams (typeParams (typeParam T)))"),
        args(AntlrParser::internalParams,
            ";T",
            "(internalParams ; (typeParams (typeParam T)))"),
        args(AntlrParser::typeDeclBody,
            "{ Foo foo(); Foo foo(); }",
            "(typeDeclBody { "
                + "(chainStmt (returnType (typeRef (name Foo))) (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;) "
                + "(chainStmt (returnType (typeRef (name Foo))) (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;) })"),
        args(AntlrParser::chainStmt,
            "Foo foo();",
            "(chainStmt (returnType (typeRef (name Foo))) (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;)"),
        args(AntlrParser::chainTerm,
            "foo()",
            "(chainTerm (chainFact (chainElem (method foo ( )))))"),
        args(AntlrParser::chainExpr,
            "foo() | foo()",
            "(chainExpr (chainTerm (chainFact (chainElem (method foo ( ))))) | (chainTerm (chainFact (chainElem (method foo ( ))))))"),
        args(AntlrParser::chainTerm,
            "foo()",
            "(chainTerm (chainFact (chainElem (method foo ( )))))"),
        args(AntlrParser::chainTerm,
            "foo() foo()",
            "(chainTerm (chainFact (chainElem (method foo ( )))) (chainFact (chainElem (method foo ( )))))"),
        args(AntlrParser::chainFact,
            "foo()",
            "(chainFact (chainElem (method foo ( ))))"),
        args(AntlrParser::chainFact,
            "foo()*",
            "(chainFact (chainElem (method foo ( ))) *)"),
        args(AntlrParser::chainFact,
            "foo()?",
            "(chainFact (chainElem (method foo ( ))) ?)"),
        args(AntlrParser::chainFact,
            "foo()+",
            "(chainFact (chainElem (method foo ( ))) +)"),
        args(AntlrParser::chainFact,
            "foo()[1]",
            "(chainFact (chainElem (method foo ( ))) (repeatN [ 1 ]))"),
        args(AntlrParser::chainFact,
            "foo()[1,]",
            "(chainFact (chainElem (method foo ( ))) (repeatNX [ 1 , ]))"),
        args(AntlrParser::chainFact,
            "foo()[1,1]",
            "(chainFact (chainElem (method foo ( ))) (repeatNM [ 1 , 1 ]))"),
        args(AntlrParser::chainElem,
            "( foo() )",
            "(chainElem ( (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ))"),
        args(AntlrParser::chainElem,
            "$FOO",
            "(chainElem (fragmentRef $FOO))"),
        args(AntlrParser::chainElem,
            "{ foo() }",
            "(chainElem (permutation { (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) }))"),
        args(AntlrParser::chainElem,
            "foo()",
            "(chainElem (method foo ( )))"),
        args(AntlrParser::returnType,
            "Foo",
            "(returnType (typeRef (name Foo)))"),
        args(AntlrParser::repeatN,
            "[1]",
            "(repeatN [ 1 ])"),
        args(AntlrParser::repeatNX,
            "[1,]",
            "(repeatNX [ 1 , ])"),
        args(AntlrParser::repeatNM,
            "[1,1]",
            "(repeatNM [ 1 , 1 ])"),
        args(AntlrParser::permutation,
            "{ foo() }",
            "(permutation { (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) })"),
        args(AntlrParser::permutation,
            "{ foo(), }",
            "(permutation { (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) , })"),
        args(AntlrParser::permutation,
            "{ foo(), foo() }",
            "(permutation { "
                + "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) , "
                + "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) })"),
        args(AntlrParser::method,
            "foo()",
            "(method foo ( ))"),
        args(AntlrParser::method,
            "foo<Foo>()",
            "(method foo < (typeParams (typeParam Foo)) > ( ))"),
        args(AntlrParser::method,
            "foo(Foo foo)",
            "(method foo ( (params (param (typeRef (name Foo)) foo)) ))"),
        args(AntlrParser::method,
            "foo() throws Foo",
            "(method foo ( ) (exceptions throws (typeRef (name Foo))))"),
        args(AntlrParser::exceptions,
            "throws Foo",
            "(exceptions throws (typeRef (name Foo)))"),
        args(AntlrParser::exceptions,
            "throws Foo, Foo",
            "(exceptions throws (typeRef (name Foo)) , (typeRef (name Foo)))"),
        args(AntlrParser::param,
            "Foo foo",
            "(param (typeRef (name Foo)) foo)"),
        args(AntlrParser::params,
            "Foo foo, Foo foo",
            "(params (param (typeRef (name Foo)) foo) , (param (typeRef (name Foo)) foo))"),
        args(AntlrParser::param,
            "Foo foo",
            "(param (typeRef (name Foo)) foo)"),
        args(AntlrParser::param,
            "Foo... foo",
            "(param (typeRef (name Foo)) ... foo)"),
        args(AntlrParser::fragmentRef,
            "$FOO",
            "(fragmentRef $FOO)"),
        args(AntlrParser::typeRef,
            "foo",
            "(typeRef (name foo))"),
        args(AntlrParser::typeRef,
            "foo<?>",
            "(typeRef (name foo) (typeArgs < (typeArg (wildcard ?)) >))"),
        args(AntlrParser::typeRef,
            "foo[]",
            "(typeRef (name foo) [])"),
        args(AntlrParser::typeRef,
            "foo[][]",
            "(typeRef (name foo) [] [])"),
        args(AntlrParser::typeArgs,
            "<?>",
            "(typeArgs < (typeArg (wildcard ?)) >)"),
        args(AntlrParser::typeArgs,
            "<?, ?>",
            "(typeArgs < (typeArg (wildcard ?)) , (typeArg (wildcard ?)) >)"),
        args(AntlrParser::typeArg,
            "Foo",
            "(typeArg (typeRef (name Foo)))"),
        args(AntlrParser::typeArg,
            "?",
            "(typeArg (wildcard ?))"),
        args(AntlrParser::wildcard,
            "?",
            "(wildcard ?)"),
        args(AntlrParser::wildcard,
            "? super Foo",
            "(wildcard ? super (typeRef (name Foo)))"),
        args(AntlrParser::wildcard,
            "? extends Foo",
            "(wildcard ? extends (typeRef (name Foo)))"),
        args(AntlrParser::typeParams,
            "T",
            "(typeParams (typeParam T))"),
        args(AntlrParser::typeParams,
            "T, S",
            "(typeParams (typeParam T) , (typeParam S))"),
        args(AntlrParser::typeParam,
            "T",
            "(typeParam T)"),
        args(AntlrParser::typeParam,
            "T extends Foo",
            "(typeParam T (bounds extends (typeRef (name Foo))))"),
        args(AntlrParser::bounds,
            "extends Foo",
            "(bounds extends (typeRef (name Foo)))"),
        args(AntlrParser::bounds,
            "extends Foo & Foo",
            "(bounds extends (typeRef (name Foo)) & (typeRef (name Foo)))"),
        args(AntlrParser::name,
            "foo.foo",
            "(name (qualifier foo .) foo)"),
        args(AntlrParser::name,
            "foo",
            "(name foo)"),
        args(AntlrParser::qualifier,
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
    AntlrParser parser = parser(text);
    ParserRuleContext ctx = selector.apply(parser);
    assertThat(ctx.toStringTree(parser)).isEqualTo(expected);
  }
}
