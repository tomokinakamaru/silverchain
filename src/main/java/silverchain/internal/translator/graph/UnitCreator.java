package silverchain.internal.translator.graph;

import com.github.javaparser.ast.CompilationUnit;
import silverchain.internal.middle.graph.data.GraphVisitor;
import silverchain.internal.middle.graph.data.attribute.TypeDeclaration;
import silverchain.internal.middle.graph.data.graph.Graph;
import silverchain.internal.middle.graph.data.graph.Node;

class UnitCreator extends GraphVisitor {

  private final PkgNameProvider pkgNameProvider;

  private final TypeNameProvider typeNameProvider;

  private final NodeUnits units = new NodeUnits();

  UnitCreator(PkgNameProvider pkgNameProvider, TypeNameProvider typeNameProvider) {
    this.pkgNameProvider = pkgNameProvider;
    this.typeNameProvider = typeNameProvider;
  }

  @Override
  protected void visit(Node node) {
    create(node, graph().typeDeclaration());
  }

  @Override
  protected void exit(Graph graph) {
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
