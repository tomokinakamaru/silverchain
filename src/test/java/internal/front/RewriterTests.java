package internal.front;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;
import silverchain.internal.front.parser.AgParser;
import silverchain.internal.front.parser.antlr.AgBaseListener;
import silverchain.internal.front.parser.antlr.AgParser.ChainExprContext;
import silverchain.internal.front.parser.antlr.AgParser.InputContext;
import silverchain.internal.front.parser.antlr.AgParser.NameContext;
import silverchain.internal.front.rewriter.FragmentResolver;
import silverchain.internal.front.rewriter.ImportResolver;
import silverchain.internal.front.rewriter.VirtualToken;

class RewriterTests {

  @Test
  void testFragmentResolver() {
    ChainExprContext expr =
        parseAndRewrite("$FOO = foo() bar(); Foo { Foo $FOO; }", new FragmentResolver())
            .typeDecl(0)
            .chainStmts()
            .chainStmt(0)
            .chainExpr()
            .chainTerm(0)
            .chainFact(0)
            .chainElem()
            .chainExpr();

    assertThat(expr.chainTerm(0).chainFact(0).chainElem().method()).isNotNull();

    VirtualToken start = (VirtualToken) expr.start;
    VirtualToken stop = (VirtualToken) expr.stop;
    assertThat(start.token().getLine()).isEqualTo(1);
    assertThat(start.token().getCharPositionInLine()).isEqualTo(30);
    assertThat(start.subToken().getLine()).isEqualTo(1);
    assertThat(start.subToken().getCharPositionInLine()).isEqualTo(7);
    assertThat(stop.token().getLine()).isEqualTo(1);
    assertThat(stop.token().getCharPositionInLine()).isEqualTo(30);
    assertThat(stop.subToken().getLine()).isEqualTo(1);
    assertThat(stop.subToken().getCharPositionInLine()).isEqualTo(17);
  }

  @Test
  void testImportResolver() {
    NameContext name =
        parseAndRewrite("import foo.Foo; Foo { Bar baz(); }", new ImportResolver())
            .typeDecl(0)
            .name();

    assertThat(name.ID().getText()).isEqualTo("Foo");
    assertThat(name.name().ID().getText()).isEqualTo("foo");

    VirtualToken start = (VirtualToken) name.start;
    VirtualToken stop = (VirtualToken) name.stop;
    assertThat(start.token().getLine()).isEqualTo(1);
    assertThat(start.token().getCharPositionInLine()).isEqualTo(16);
    assertThat(start.subToken().getLine()).isEqualTo(1);
    assertThat(start.subToken().getCharPositionInLine()).isEqualTo(7);
    assertThat(stop.token().getLine()).isEqualTo(1);
    assertThat(stop.token().getCharPositionInLine()).isEqualTo(16);
    assertThat(stop.subToken().getLine()).isEqualTo(1);
    assertThat(stop.subToken().getCharPositionInLine()).isEqualTo(11);
  }

  private static InputContext parseAndRewrite(String text, AgBaseListener listener) {
    InputContext ctx = new AgParser().parse(CharStreams.fromString(text));
    ParseTreeWalker.DEFAULT.walk(listener, ctx);
    return ctx;
  }
}
