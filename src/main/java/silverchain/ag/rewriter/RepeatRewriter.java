package silverchain.ag.rewriter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apiguardian.api.API;
import silverchain.ag.AgListener;
import silverchain.ag.antlr.AgLexer;
import silverchain.ag.antlr.AgParser.ChainElemContext;
import silverchain.ag.antlr.AgParser.ChainFactContext;
import silverchain.ag.antlr.AgParser.ChainTermContext;
import silverchain.ag.antlr.AgParser.RepeatNContext;
import silverchain.ag.antlr.AgParser.RepeatNMContext;
import silverchain.ag.antlr.AgParser.RepeatNXContext;

@API(status = API.Status.INTERNAL)
public class RepeatRewriter extends AgListener {

  @Override
  public void exitChainTerm(ChainTermContext ctx) {
    List<ParseTree> children = new ArrayList<>();
    ctx.chainFact().stream().flatMap(this::rewrite).forEach(children::add);
    ctx.children.clear();
    children.forEach(c -> AgAssembler.add(ctx, c));
  }

  protected Stream<ChainFactContext> rewrite(ChainFactContext ctx) {
    if (ctx.repeatN() != null) return rewrite(ctx.chainElem(), ctx.repeatN());
    if (ctx.repeatNX() != null) return rewrite(ctx.chainElem(), ctx.repeatNX());
    if (ctx.repeatNM() != null) return rewrite(ctx.chainElem(), ctx.repeatNM());
    return Stream.of(ctx);
  }

  protected Stream<ChainFactContext> rewrite(ChainElemContext elem, RepeatNContext repeat) {
    int n = Integer.parseInt(repeat.INT().getText());
    return repeat(AgFactory.fact(elem), n);
  }

  protected Stream<ChainFactContext> rewrite(ChainElemContext elem, RepeatNXContext repeat) {
    int n = Integer.parseInt(repeat.INT().getText());
    return Stream.concat(repeat(AgFactory.fact(elem), n), Stream.of(zeroMore(elem)));
  }

  protected Stream<ChainFactContext> rewrite(ChainElemContext elem, RepeatNMContext repeat) {
    int min = Integer.parseInt(repeat.INT(0).getText());
    int max = Integer.parseInt(repeat.INT(1).getText());
    return Stream.concat(repeat(AgFactory.fact(elem), min), repeat(zeroOne(elem), max - min));
  }

  protected Stream<ChainFactContext> repeat(ChainFactContext ctx, int n) {
    return IntStream.range(0, n).mapToObj(i -> AgReplicator.replicate(ctx));
  }

  protected ChainFactContext zeroMore(ChainElemContext ctx) {
    return AgFactory.fact(ctx, AgFactory.terminal(AgLexer.ASTERISK));
  }

  protected ChainFactContext zeroOne(ChainElemContext ctx) {
    return AgFactory.fact(ctx, AgFactory.terminal(AgLexer.QUESTION));
  }
}
