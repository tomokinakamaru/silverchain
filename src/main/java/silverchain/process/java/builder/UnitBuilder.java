package silverchain.process.java.builder;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import silverchain.data.graph.Edge;
import silverchain.data.graph.Graph;
import silverchain.data.graph.Node;
import silverchain.data.graph.attribute.Label;
import silverchain.data.graph.attribute.Method;
import silverchain.data.graph.attribute.ReturnType;
import silverchain.data.graph.visitor.GraphListener;

class UnitBuilder implements GraphListener {

  private final NodeUnits units;

  UnitBuilder(NodeUnits units) {
    this.units = units;
  }

  @Override
  public void visit(Graph graph, Node node) {
    ClassOrInterfaceDeclaration decl = units.get(node);
    if (decl != null) build(node, decl);
  }

  @Override
  public void exit(Graph graph) {
    // return units.get();
  }

  private void build(Node node, ClassOrInterfaceDeclaration decl) {
    decl.setPublic(true);
    decl.setInterface(true);
    decl.setTypeParameters(AttrEncoder.encode(node.parameters()));
    for (Edge edge : node.edges()) build(decl, edge);
  }

  private void build(ClassOrInterfaceDeclaration decl, Edge edge) {
    Label label = edge.label();
    if (label instanceof Method) build(decl, (Method) label, edge.target());
  }

  private void build(ClassOrInterfaceDeclaration decl, Method method, Node target) {
    MethodDeclaration m = AttrEncoder.encode(method);
    ClassOrInterfaceDeclaration c = units.get(target);
    if (c == null) build(m, target);
    else build(m, c);
    decl.addMember(m);
  }

  private void build(MethodDeclaration m, ClassOrInterfaceDeclaration c) {
    m.setType(c.getFullyQualifiedName().toString());
  }

  private void build(MethodDeclaration decl, Node node) {
    ReturnType returnType = findRetType(node);
    if (returnType == null || returnType.type() == null) decl.setType("void");
    else decl.setType(AttrEncoder.encode(returnType));
  }

  private static ReturnType findRetType(Node node) {
    return node.edges().stream()
        .map(Edge::label)
        .filter(l -> l instanceof ReturnType)
        .map(l -> (ReturnType) l)
        .findFirst()
        .orElse(null);
  }
}
