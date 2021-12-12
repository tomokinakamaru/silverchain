package silverchain.internal.middle.graph.parser;

import java.util.stream.Collectors;
import silverchain.internal.front.parser.antlr.AgBaseListener;
import silverchain.internal.front.parser.antlr.AgParser.InputContext;
import silverchain.internal.middle.graph.data.graph.collection.Graphs;

public class AgCompiler extends AgBaseListener {

  public Graphs compile(InputContext ctx) {
    return ctx.typeDecl().stream()
        .map(d -> d.accept(new GraphBuilder()))
        .collect(Collectors.toCollection(Graphs::new));
  }
}
