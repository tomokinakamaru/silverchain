package silverchain.internal.translator.graph;

import silverchain.internal.middle.graph.ir.GraphVisitor;
import silverchain.internal.middle.graph.ir.graph.collection.Graphs;
import silverchain.internal.middle.java.CompilationUnits;

public class GraphTranslator extends GraphVisitor {

  private final PkgNameProvider pkgNameProvider;

  private final TypeNameProvider typeNameProvider;

  public GraphTranslator() {
    this.pkgNameProvider = null;
    this.typeNameProvider = null;
  }

  public CompilationUnits translate(Graphs graphs) {
    // UnitCreator creator = new UnitCreator(pkgNameProvider, typeNameProvider);
    // UnitBuilder builder = new UnitBuilder(creator.travel(graph));
    // return builder.travel(graph);
    return null;
  }
}
