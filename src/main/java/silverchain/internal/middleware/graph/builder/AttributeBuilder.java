package silverchain.internal.middleware.graph.builder;

import java.util.stream.Collectors;
import silverchain.internal.frontend.parser.antlr.AgParser.BoundsContext;
import silverchain.internal.frontend.parser.antlr.AgParser.ExceptionsContext;
import silverchain.internal.frontend.parser.antlr.AgParser.MethodContext;
import silverchain.internal.frontend.parser.antlr.AgParser.NameContext;
import silverchain.internal.frontend.parser.antlr.AgParser.ParamContext;
import silverchain.internal.frontend.parser.antlr.AgParser.ParamsContext;
import silverchain.internal.frontend.parser.antlr.AgParser.ReturnTypeContext;
import silverchain.internal.frontend.parser.antlr.AgParser.TypeArgContext;
import silverchain.internal.frontend.parser.antlr.AgParser.TypeArgsContext;
import silverchain.internal.frontend.parser.antlr.AgParser.TypeDeclContext;
import silverchain.internal.frontend.parser.antlr.AgParser.TypeParamContext;
import silverchain.internal.frontend.parser.antlr.AgParser.TypeParamsContext;
import silverchain.internal.frontend.parser.antlr.AgParser.TypeRefContext;
import silverchain.internal.frontend.parser.antlr.AgParser.WildcardContext;
import silverchain.internal.middleware.graph.data.attribute.Method;
import silverchain.internal.middleware.graph.data.attribute.Name;
import silverchain.internal.middleware.graph.data.attribute.Parameter;
import silverchain.internal.middleware.graph.data.attribute.ReturnType;
import silverchain.internal.middleware.graph.data.attribute.TypeArgument;
import silverchain.internal.middleware.graph.data.attribute.TypeDeclaration;
import silverchain.internal.middleware.graph.data.attribute.TypeParameter;
import silverchain.internal.middleware.graph.data.attribute.TypeReference;
import silverchain.internal.middleware.graph.data.attribute.Wildcard;
import silverchain.internal.middleware.graph.data.attribute.collection.Bounds;
import silverchain.internal.middleware.graph.data.attribute.collection.Exceptions;
import silverchain.internal.middleware.graph.data.attribute.collection.Parameters;
import silverchain.internal.middleware.graph.data.attribute.collection.TypeArguments;
import silverchain.internal.middleware.graph.data.attribute.collection.TypeParameters;

public final class AttributeBuilder {

  private AttributeBuilder() {}

  public static Method build(MethodContext ctx) {
    if (ctx == null) return null;
    Method method = new Method();
    method.name(ctx.ID().getText());
    method.typeParameters(build(ctx.typeParams()));
    method.parameters(build(ctx.params()));
    method.exceptions(build(ctx.exceptions()));
    method.locations(LocationBuilder.build(ctx.start));
    return method;
  }

  public static Name build(NameContext ctx) {
    if (ctx == null) return null;
    Name name = new Name();
    name.id(ctx.ID().getText());
    name.qualifier(build(ctx.name()));
    return name;
  }

  public static Parameter build(ParamContext ctx) {
    if (ctx == null) return null;
    Parameter parameter = new Parameter();
    parameter.type(build(ctx.typeRef()));
    parameter.varargs(ctx.ELLIPSIS != null);
    parameter.name(ctx.ID().getText());
    return parameter;
  }

  public static ReturnType build(ReturnTypeContext ctx) {
    if (ctx == null) return null;
    ReturnType returnType = new ReturnType();
    returnType.type(build(ctx.typeRef()));
    returnType.locations(LocationBuilder.build(ctx.start));
    return returnType;
  }

  public static TypeArgument build(TypeArgContext ctx) {
    if (ctx == null) return null;
    return ctx.typeRef() == null ? build(ctx.wildcard()) : build(ctx.typeRef());
  }

  public static TypeDeclaration build(TypeDeclContext ctx) {
    if (ctx == null) return null;
    TypeDeclaration typeDeclaration = new TypeDeclaration();
    typeDeclaration.name(build(ctx.name()));
    typeDeclaration.externalParameters(build(ctx.external));
    typeDeclaration.internalParameters(build(ctx.internal));
    return typeDeclaration;
  }

  public static TypeParameter build(TypeParamContext ctx) {
    if (ctx == null) return null;
    TypeParameter typeParameter = new TypeParameter();
    typeParameter.name(ctx.ID().getText());
    typeParameter.bounds(build(ctx.bounds()));
    return typeParameter;
  }

  public static TypeReference build(TypeRefContext ctx) {
    if (ctx == null) return null;
    TypeReference typeReference = new TypeReference();
    typeReference.name(build(ctx.name()));
    typeReference.arguments(build(ctx.typeArgs()));
    typeReference.dimension(ctx.ARRAY().size());
    return typeReference;
  }

  public static Wildcard build(WildcardContext ctx) {
    if (ctx == null) return null;
    Wildcard wildcard = new Wildcard();
    wildcard.upperBound(ctx.EXTENDS != null);
    wildcard.bound(build(ctx.typeRef()));
    return wildcard;
  }

  public static Bounds build(BoundsContext ctx) {
    if (ctx == null) return new Bounds();
    return ctx.typeRef().stream()
        .map(AttributeBuilder::build)
        .collect(Collectors.toCollection(Bounds::new));
  }

  public static Exceptions build(ExceptionsContext ctx) {
    if (ctx == null) return new Exceptions();
    return ctx.typeRef().stream()
        .map(AttributeBuilder::build)
        .collect(Collectors.toCollection(Exceptions::new));
  }

  public static Parameters build(ParamsContext ctx) {
    if (ctx == null) return new Parameters();
    return ctx.param().stream()
        .map(AttributeBuilder::build)
        .collect(Collectors.toCollection(Parameters::new));
  }

  public static TypeArguments build(TypeArgsContext ctx) {
    if (ctx == null) return new TypeArguments();
    return ctx.typeArg().stream()
        .map(AttributeBuilder::build)
        .collect(Collectors.toCollection(TypeArguments::new));
  }

  public static TypeParameters build(TypeParamsContext ctx) {
    if (ctx == null) return new TypeParameters();
    return ctx.typeParam().stream()
        .map(AttributeBuilder::build)
        .collect(Collectors.toCollection(TypeParameters::new));
  }
}
