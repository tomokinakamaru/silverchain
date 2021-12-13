package silverchain.internal.middleware.java.builder;

import com.github.javaparser.ast.CompilationUnit;
import silverchain.internal.middleware.graph.data.GraphListener;
import silverchain.internal.middleware.graph.data.attribute.TypeDeclaration;
import silverchain.internal.middleware.graph.data.graph.Graph;
import silverchain.internal.middleware.graph.data.graph.Node;

class UnitCreator implements GraphListener {

  private final PkgNameProvider pkgNameProvider;

  private final TypeNameProvider typeNameProvider;

  private final NodeUnits units = new NodeUnits();

  UnitCreator(PkgNameProvider pkgNameProvider, TypeNameProvider typeNameProvider) {
    this.pkgNameProvider = pkgNameProvider;
    this.typeNameProvider = typeNameProvider;
  }

  @Override
  public void visit(Graph graph, Node node) {
    create(node, graph.typeDeclaration());
  }

  @Override
  public void exit(Graph graph) {
    // return units;
  }

  private void create(Node node, TypeDeclaration typeDeclaration) {
    String pkgName = pkgNameProvider.apply(typeDeclaration, node);
    String typeName = typeNameProvider.apply(typeDeclaration, node);
    if (typeName != null) create(node, pkgName, typeName);
  }

  private void create(Node node, String pkgName, String typeName) {
    units.put(node, new CompilationUnit(pkgName)).addClass(typeName);
  }
}
