package silverchain.internal.translator.tree;

import java.util.stream.Collectors;
import silverchain.internal.front.parser.AgBaseListener;
import silverchain.internal.front.parser.AgParser.InputContext;
import silverchain.internal.middle.graph.data.graph.collection.Graphs;

public class TreeCompiler extends AgBaseListener {

  public Graphs compile(InputContext ctx) {
    return ctx.typeDecl().stream()
        .map(d -> d.accept(new GraphBuilder()))
        .collect(Collectors.toCollection(Graphs::new));
  }
}
