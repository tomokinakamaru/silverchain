package internal.frontend.rewriter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.internal.frontend.antlr.AgLexer;
import silverchain.internal.frontend.antlr.AgParser;
import silverchain.internal.frontend.antlr.AgParser.InputContext;
import silverchain.internal.frontend.rewriter.FragmentResolver;
import silverchain.internal.frontend.rewriter.ImportResolver;
import silverchain.internal.frontend.utility.ContextRewriter;

class RewriterTest {

  private static Arguments[] testData() {
    return new Arguments[] {
      Arguments.of(
          "import foo.Foo; Foo { Foo foo(); }",
          new ImportResolver(),
          "(input "
              + "(typeDecl (name (qualifier foo .) Foo) { (chainStmts (chainStmt "
              + "(returnType (typeRef (name (qualifier foo .) Foo))) "
              + "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;)) }) <EOF>)"),
      Arguments.of(
          "$FOO = foo(); Foo { Foo $FOO; }",
          new FragmentResolver(),
          "(input "
              + "(typeDecl (name Foo) { (chainStmts (chainStmt (returnType (typeRef (name Foo))) "
              + "(chainExpr (chainTerm (chainFact (chainElem "
              + "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))))))) ;)) }) <EOF>)"),
      Arguments.of(
          "$FOO = foo(); $BAR = $FOO; Foo { Foo $BAR; }",
          new FragmentResolver(),
          "(input "
              + "(typeDecl (name Foo) { (chainStmts (chainStmt (returnType (typeRef (name Foo))) "
              + "(chainExpr (chainTerm (chainFact (chainElem (chainExpr (chainTerm (chainFact (chainElem "
              + "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))))))))))) ;)) }) <EOF>)")
    };
  }

  @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{2}\"")
  @MethodSource("testData")
  void test(String text, ContextRewriter rewriter, String tree) {
    AgLexer lexer = new AgLexer(CharStreams.fromString(text));
    AgParser parser = new AgParser(new CommonTokenStream(lexer));
    InputContext ctx = (InputContext) parser.input().accept(rewriter);
    assertThat(ctx.toStringTree(parser)).isEqualTo(tree);
  }
}
