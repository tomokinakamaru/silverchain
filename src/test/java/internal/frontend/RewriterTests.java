package internal.frontend;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.internal.frontend.parser.antlr.AgBaseListener;
import silverchain.internal.frontend.parser.antlr.AgLexer;
import silverchain.internal.frontend.parser.antlr.AgParser;
import silverchain.internal.frontend.parser.antlr.AgParser.InputContext;
import silverchain.internal.frontend.rewriter.FragmentResolver;
import silverchain.internal.frontend.rewriter.ImportResolver;

class RewriterTests {

  private static Arguments[] testData() {
    return new Arguments[] {
      Arguments.of(
          "import foo.Foo; Foo { Foo foo(); }",
          new ImportResolver(),
          "(input (importDecl import (name (qualifier foo .) Foo) ;) "
              + "(typeDecl (name (qualifier foo .) Foo) { (chainStmts (chainStmt "
              + "(returnType (typeRef (name (qualifier foo .) Foo))) "
              + "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;)) }) <EOF>)"),
      Arguments.of(
          "$FOO = foo(); Foo { Foo $FOO; }",
          new FragmentResolver(),
          "(input (fragmentDecl $FOO = (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;) "
              + "(typeDecl (name Foo) { (chainStmts (chainStmt (returnType (typeRef (name Foo))) "
              + "(chainExpr (chainTerm (chainFact (chainElem "
              + "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))))))) ;)) }) <EOF>)"),
      Arguments.of(
          "$FOO = foo(); $BAR = $FOO; Foo { Foo $BAR; }",
          new FragmentResolver(),
          "(input "
              + "(fragmentDecl $FOO = (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))) ;) "
              + "(fragmentDecl $BAR = (chainExpr (chainTerm (chainFact (chainElem "
              + "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))))))) ;) "
              + "(typeDecl (name Foo) { (chainStmts (chainStmt (returnType (typeRef (name Foo))) "
              + "(chainExpr (chainTerm (chainFact (chainElem (chainExpr (chainTerm (chainFact (chainElem "
              + "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))))))))))) ;)) }) <EOF>)")
    };
  }

  @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{2}\"")
  @MethodSource("testData")
  void test(String text, AgBaseListener listener, String tree) {
    AgLexer lexer = new AgLexer(CharStreams.fromString(text));
    AgParser parser = new AgParser(new CommonTokenStream(lexer));
    InputContext ctx = parser.input();
    ParseTreeWalker.DEFAULT.walk(listener, ctx);
    assertThat(ctx.toStringTree(parser)).isEqualTo(tree);
  }
}
