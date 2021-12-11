package silverchain.internal.middle.graph.rewriter;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;
import silverchain.internal.middle.graph.ir.AttributeVisitor;
import silverchain.internal.middle.graph.ir.GraphVisitor;
import silverchain.internal.middle.graph.ir.attribute.Method;
import silverchain.internal.middle.graph.ir.attribute.TypeParameter;
import silverchain.internal.middle.graph.ir.attribute.TypeReference;
import silverchain.internal.middle.graph.ir.attribute.collection.TypeParameters;
import silverchain.internal.middle.graph.ir.graph.Edge;
import silverchain.internal.middle.graph.ir.graph.Graph;

public class ParamRefResolver extends GraphVisitor implements AttributeVisitor<Void, Void> {

  protected TypeParameters externalTypeParameters;

  protected TypeParameters internalTypeParameters;

  protected TypeParameters methodTypeParameters;

  @Override
  protected void enter(Graph graph) {
    externalTypeParameters = graph.typeDeclaration().externalParameters();
    internalTypeParameters = graph.typeDeclaration().internalParameters();
    graph.typeDeclaration().accept(this, null);
  }

  @Override
  protected void visit(Edge edge) {
    if (edge.label() != null) edge.label().accept(this, null);
  }

  @Override
  public Void visit(Method method, Void arg) {
    methodTypeParameters = method.typeParameters();
    AttributeVisitor.super.visit(method, arg);
    methodTypeParameters = null;
    return null;
  }

  @Override
  public Void visit(TypeReference typeReference, Void arg) {
    if (typeReference.name().qualifier() == null) typeReference.target(find(typeReference));
    AttributeVisitor.super.visit(typeReference, arg);
    return null;
  }

  protected TypeParameter find(TypeReference typeReference) {
    return Stream.of(methodTypeParameters, internalTypeParameters, externalTypeParameters)
        .filter(Objects::nonNull)
        .flatMap(Collection::stream)
        .filter(p -> p.name().equals(typeReference.name().id()))
        .findFirst()
        .orElse(null);
  }
}
