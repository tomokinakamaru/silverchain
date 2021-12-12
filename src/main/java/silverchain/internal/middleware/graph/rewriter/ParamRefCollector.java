package silverchain.internal.middleware.graph.rewriter;

import silverchain.internal.middleware.graph.data.AttributeVisitor;
import silverchain.internal.middleware.graph.data.attribute.TypeReference;
import silverchain.internal.middleware.graph.data.attribute.collection.TypeParameters;

public class ParamRefCollector implements AttributeVisitor<TypeParameters, TypeParameters> {

  @Override
  public TypeParameters visit(TypeReference typeReference, TypeParameters arg) {
    if (typeReference.target() != null) arg.add(typeReference.target());
    return AttributeVisitor.super.visit(typeReference, arg);
  }

  @Override
  public TypeParameters defaultResult(TypeParameters arg) {
    return arg;
  }
}
