package internal.front;

import java.util.function.Function;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.TokenStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.internal.front.parser.antlr.AgLexer;
import silverchain.internal.front.parser.antlr.AgParser;

class AntlrParserTests {

  private static final ErrorListener ERROR_LISTENER = new ErrorListener();

  private static Arguments[] testData() {
    return new Arguments[] {
      args(AgParser::input, ""),
      args(AgParser::importDecl, "import foo;"),
      args(AgParser::fragmentDecl, "$FOO = foo();"),
      args(AgParser::typeDecl, "Foo { Bar baz(); }"),
      args(AgParser::typeDecl, "Foo<T> { Bar baz(); }"),
      args(AgParser::typeDecl, "Foo<T;S> { Bar baz(); }"),
      args(AgParser::typeDecl, "Foo<;T> { Bar baz(); }"),
      args(AgParser::chainStmts, "Foo foo(); Bar bar();"),
      args(AgParser::chainStmt, "Foo foo();"),
      args(AgParser::chainExpr, "foo() | bar()"),
      args(AgParser::chainTerm, "foo() bar()"),
      args(AgParser::chainFact, "foo()*"),
      args(AgParser::returnType, "void"),
      args(AgParser::repeat, "*"),
      args(AgParser::repeat, "?"),
      args(AgParser::repeat, "+"),
      args(AgParser::repeatSugar, "[1]"),
      args(AgParser::repeatSugar, "[1,]"),
      args(AgParser::repeatSugar, "[1,2]"),
      args(AgParser::permutation, "{ foo() }"),
      args(AgParser::permutation, "{ foo(), }"),
      args(AgParser::permutation, "{ foo(), bar() }"),
      args(AgParser::permutation, "{ foo(), bar(), }"),
      args(AgParser::method, "foo<T>()"),
      args(AgParser::method, "foo(Foo foo)"),
      args(AgParser::method, "foo() throws Foo"),
      args(AgParser::exceptions, "throws Foo, Bar"),
      args(AgParser::params, "Foo foo, Bar bar"),
      args(AgParser::param, "Foo... foo"),
      args(AgParser::fragmentRef, "$FOO"),
      args(AgParser::typeRef, "Foo<Bar>"),
      args(AgParser::typeRef, "Foo[]"),
      args(AgParser::typeRef, "Foo[ ]"),
      args(AgParser::typeRef, "Foo[][]"),
      args(AgParser::typeArgs, "<T,S>"),
      args(AgParser::typeArg, "Foo"),
      args(AgParser::typeArg, "?"),
      args(AgParser::wildcard, "? super Foo"),
      args(AgParser::wildcard, "? extends Foo"),
      args(AgParser::typeParams, "T, S"),
      args(AgParser::typeParam, "T extends Foo"),
      args(AgParser::bounds, "extends Foo & Bar"),
      args(AgParser::name, "foo"),
      args(AgParser::name, "foo.foo"),
      args(AgParser::name, "foo.foo.foo"),
    };
  }

  private static Arguments args(Function<AgParser, ParserRuleContext> selector, String text) {
    return Arguments.of(selector, text);
  }

  @ParameterizedTest(name = "[{index}] \"{1}\"")
  @MethodSource("testData")
  void test(Function<AgParser, ParserRuleContext> selector, String text) {
    CharStream charStream = CharStreams.fromString(text);
    AgLexer lexer = new AgLexer(charStream);
    TokenStream tokenStream = new CommonTokenStream(lexer);
    AgParser parser = new AgParser(tokenStream);
    parser.removeErrorListeners();
    parser.addErrorListener(ERROR_LISTENER);
    selector.apply(parser);
  }

  private static class ErrorListener extends BaseErrorListener {
    @Override
    public void syntaxError(
        Recognizer<?, ?> recognizer,
        Object offendingSymbol,
        int line,
        int charPositionInLine,
        String msg,
        RecognitionException e) {
      throw new RuntimeException("L" + line + "C" + charPositionInLine + ":" + msg);
    }
  }
}
