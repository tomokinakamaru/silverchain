package silverchain.internal.middle.graph.rewriter;

import silverchain.internal.middle.data.AttributeVisitor;
import silverchain.internal.middle.data.attribute.TypeReference;
import silverchain.internal.middle.data.attribute.collection.TypeParameters;

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
