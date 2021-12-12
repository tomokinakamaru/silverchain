package silverchain.internal.middleware.graph;

import silverchain.internal.frontend.parser.antlr.AgParser.InputContext;
import silverchain.internal.middleware.graph.builder.AgCompiler;
import silverchain.internal.middleware.graph.checker.EdgeConflictValidator;
import silverchain.internal.middleware.graph.data.graph.collection.Graphs;
import silverchain.internal.middleware.graph.rewriter.GraphDeterminizer;
import silverchain.internal.middleware.graph.rewriter.GraphReverser;
import silverchain.internal.middleware.graph.rewriter.ParamPropagator;
import silverchain.internal.middleware.graph.rewriter.ParamRefResolver;

public class GraphMiddleware {

  public Graphs run(InputContext ctx) {
    Graphs graphs = new AgCompiler().compile(ctx);
    new GraphReverser().visit(graphs);
    new GraphDeterminizer().visit(graphs);
    new GraphReverser().visit(graphs);
    new GraphDeterminizer().visit(graphs);
    new ParamRefResolver().visit(graphs);
    new ParamPropagator().visit(graphs);
    new EdgeConflictValidator().visit(graphs);
    return graphs;
  }
}
