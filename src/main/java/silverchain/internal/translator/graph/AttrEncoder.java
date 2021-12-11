package silverchain.internal.translator.graph;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.WildcardType;
import java.util.stream.Collectors;
import silverchain.internal.middle.graph.ir.attribute.Method;
import silverchain.internal.middle.graph.ir.attribute.Name;
import silverchain.internal.middle.graph.ir.attribute.Parameter;
import silverchain.internal.middle.graph.ir.attribute.ReturnType;
import silverchain.internal.middle.graph.ir.attribute.TypeArgument;
import silverchain.internal.middle.graph.ir.attribute.TypeParameter;
import silverchain.internal.middle.graph.ir.attribute.TypeReference;
import silverchain.internal.middle.graph.ir.attribute.Wildcard;
import silverchain.internal.middle.graph.ir.attribute.collection.Bounds;
import silverchain.internal.middle.graph.ir.attribute.collection.Exceptions;
import silverchain.internal.middle.graph.ir.attribute.collection.Parameters;
import silverchain.internal.middle.graph.ir.attribute.collection.TypeArguments;
import silverchain.internal.middle.graph.ir.attribute.collection.TypeParameters;

class AttrEncoder {

  static MethodDeclaration encode(Method method) {
    if (method == null) return null;
    MethodDeclaration m = new MethodDeclaration();
    m.setTypeParameters(encode(method.typeParameters()));
    m.setName(method.name());
    m.setParameters(encode(method.parameters()));
    m.setThrownExceptions(encode(method.exceptions()));
    return m;
  }

  static Type encode(ReturnType returnType) {
    if (returnType == null) return null;
    return encode(returnType.type());
  }

  static NodeList<com.github.javaparser.ast.type.TypeParameter> encode(
      TypeParameters typeParameters) {
    if (typeParameters == null) return null;
    return typeParameters.stream()
        .map(AttrEncoder::encode)
        .collect(Collectors.toCollection(NodeList::new));
  }

  private static com.github.javaparser.ast.expr.Name encode(Name name) {
    if (name == null) return null;
    com.github.javaparser.ast.expr.Name n = new com.github.javaparser.ast.expr.Name();
    n.setIdentifier(name.id());
    n.setQualifier(encode(name.qualifier()));
    return n;
  }

  private static com.github.javaparser.ast.body.Parameter encode(Parameter parameter) {
    if (parameter == null) return null;
    com.github.javaparser.ast.body.Parameter p = new com.github.javaparser.ast.body.Parameter();
    p.setType(encode(parameter.type()));
    p.setVarArgs(parameter.varargs());
    p.setName(parameter.name());
    return p;
  }

  private static NodeList<com.github.javaparser.ast.body.Parameter> encode(Parameters parameters) {
    if (parameters == null) return null;
    return parameters.stream()
        .map(AttrEncoder::encode)
        .collect(Collectors.toCollection(NodeList::new));
  }

  private static NodeList<ReferenceType> encode(Exceptions exceptions) {
    if (exceptions == null) return null;
    return exceptions.stream()
        .map(AttrEncoder::encode)
        .map(t -> (ReferenceType) t)
        .collect(Collectors.toCollection(NodeList::new));
  }

  private static Type encode(TypeArgument typeArgument) {
    if (typeArgument == null) return null;
    if (typeArgument instanceof TypeReference) return encode((TypeReference) typeArgument);
    return encode((Wildcard) typeArgument);
  }

  private static NodeList<Type> encode(TypeArguments typeArguments) {
    if (typeArguments == null) return null;
    return typeArguments.stream()
        .map(AttrEncoder::encode)
        .collect(Collectors.toCollection(NodeList::new));
  }

  private static com.github.javaparser.ast.type.TypeParameter encode(TypeParameter typeParameter) {
    if (typeParameter == null) return null;
    com.github.javaparser.ast.type.TypeParameter t =
        new com.github.javaparser.ast.type.TypeParameter();
    t.setName(typeParameter.name());
    t.setTypeBound(encode(typeParameter.bounds()));
    return t;
  }

  private static Type encode(TypeReference typeReference) {
    if (typeReference == null) return null;
    ClassOrInterfaceType c = new ClassOrInterfaceType();
    c.setName(encode(typeReference.name()).asString());
    c.setTypeArguments(encode(typeReference.arguments()));
    Type t = c;
    for (int i = 0; i < typeReference.dimension(); i++) {
      t = new ArrayType(c);
    }
    return t;
  }

  protected static NodeList<ClassOrInterfaceType> encode(Bounds bounds) {
    return bounds.stream()
        .map(AttrEncoder::encode)
        .map(t -> (ClassOrInterfaceType) t)
        .collect(Collectors.toCollection(NodeList::new));
  }

  private static WildcardType encode(Wildcard wildcard) {
    if (wildcard == null) return null;
    WildcardType w = new WildcardType();
    if (wildcard.bound() != null) {
      ReferenceType t = (ReferenceType) encode(wildcard.bound());
      if (wildcard.upperBound()) w.setExtendedType(t);
      else w.setSuperType(t);
    }
    return w;
  }
}
