package silverchain.internal.middleware.java.builder;

import silverchain.internal.middleware.graph.data.GraphListener;
import silverchain.internal.middleware.graph.data.graph.collection.Graphs;
import silverchain.internal.middleware.java.data.CompilationUnits;

public class GraphTranslator implements GraphListener {

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
