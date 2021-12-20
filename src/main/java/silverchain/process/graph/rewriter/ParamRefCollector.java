package silverchain.process.graph.rewriter;

import silverchain.data.graph.attribute.TypeReference;
import silverchain.data.graph.attribute.collection.TypeParameters;
import silverchain.data.graph.visitor.AttributeVisitor;

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
