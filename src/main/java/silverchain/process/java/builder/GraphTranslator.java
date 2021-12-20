package silverchain.process.java.builder;

import org.apiguardian.api.API;
import silverchain.data.graph.Graphs;
import silverchain.data.graph.visitor.GraphListener;
import silverchain.data.java.CompilationUnits;

@API(status = API.Status.INTERNAL)
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
