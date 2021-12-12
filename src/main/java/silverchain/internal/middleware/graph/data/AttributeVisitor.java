package silverchain.internal.middleware.graph.data;

import java.util.Iterator;
import java.util.stream.Stream;
import silverchain.internal.middleware.graph.data.attribute.Attribute;
import silverchain.internal.middleware.graph.data.attribute.Method;
import silverchain.internal.middleware.graph.data.attribute.Name;
import silverchain.internal.middleware.graph.data.attribute.Parameter;
import silverchain.internal.middleware.graph.data.attribute.ReturnType;
import silverchain.internal.middleware.graph.data.attribute.TypeDeclaration;
import silverchain.internal.middleware.graph.data.attribute.TypeParameter;
import silverchain.internal.middleware.graph.data.attribute.TypeReference;
import silverchain.internal.middleware.graph.data.attribute.Wildcard;
import silverchain.internal.middleware.graph.data.attribute.collection.Bounds;
import silverchain.internal.middleware.graph.data.attribute.collection.Exceptions;
import silverchain.internal.middleware.graph.data.attribute.collection.Parameters;
import silverchain.internal.middleware.graph.data.attribute.collection.TypeArguments;
import silverchain.internal.middleware.graph.data.attribute.collection.TypeParameters;

public interface AttributeVisitor<R, A> {

  default R visit(Method method, A arg) {
    return visit(method.children(), arg);
  }

  default R visit(Name name, A arg) {
    return visit(name.children(), arg);
  }

  default R visit(Parameter parameter, A arg) {
    return visit(parameter.children(), arg);
  }

  default R visit(ReturnType returnType, A arg) {
    return visit(returnType.children(), arg);
  }

  default R visit(TypeDeclaration typeDeclaration, A arg) {
    return visit(typeDeclaration.children(), arg);
  }

  default R visit(TypeParameter typeParameter, A arg) {
    return visit(typeParameter.children(), arg);
  }

  default R visit(TypeReference typeReference, A arg) {
    return visit(typeReference.children(), arg);
  }

  default R visit(Wildcard wildcard, A arg) {
    return visit(wildcard.children(), arg);
  }

  default R visit(Bounds bounds, A arg) {
    return visit(bounds.children(), arg);
  }

  default R visit(Exceptions exceptions, A arg) {
    return visit(exceptions.children(), arg);
  }

  default R visit(Parameters parameters, A arg) {
    return visit(parameters.children(), arg);
  }

  default R visit(TypeArguments typeArguments, A arg) {
    return visit(typeArguments.children(), arg);
  }

  default R visit(TypeParameters typeParameters, A arg) {
    return visit(typeParameters.children(), arg);
  }

  default R visit(Stream<? extends Attribute> attributes, A arg) {
    R result = defaultResult(arg);
    Iterator<? extends Attribute> iterator = attributes.iterator();
    while (iterator.hasNext()) {
      Attribute attribute = iterator.next();
      if (attribute != null) {
        R r = attribute.accept(this, arg);
        result = aggregate(result, r);
      }
    }
    return result;
  }

  default R aggregate(R result1, R result2) {
    return result2 == null ? result1 : result2;
  }

  default R defaultResult(A arg) {
    return null;
  }
}
