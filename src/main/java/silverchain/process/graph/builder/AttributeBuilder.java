package silverchain.process.graph.builder;

import java.util.stream.Collectors;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apiguardian.api.API;
import silverchain.ag.antlr.AgParser.BoundsContext;
import silverchain.ag.antlr.AgParser.ExceptionsContext;
import silverchain.ag.antlr.AgParser.ExternalParamsContext;
import silverchain.ag.antlr.AgParser.InternalParamsContext;
import silverchain.ag.antlr.AgParser.MethodContext;
import silverchain.ag.antlr.AgParser.NameContext;
import silverchain.ag.antlr.AgParser.ParamContext;
import silverchain.ag.antlr.AgParser.ParamsContext;
import silverchain.ag.antlr.AgParser.QualifierContext;
import silverchain.ag.antlr.AgParser.ReturnTypeContext;
import silverchain.ag.antlr.AgParser.TypeArgContext;
import silverchain.ag.antlr.AgParser.TypeArgsContext;
import silverchain.ag.antlr.AgParser.TypeDeclContext;
import silverchain.ag.antlr.AgParser.TypeParamContext;
import silverchain.ag.antlr.AgParser.TypeParamsContext;
import silverchain.ag.antlr.AgParser.TypeRefContext;
import silverchain.ag.antlr.AgParser.WildcardContext;
import silverchain.data.graph.attribute.Method;
import silverchain.data.graph.attribute.Name;
import silverchain.data.graph.attribute.Parameter;
import silverchain.data.graph.attribute.Qualifier;
import silverchain.data.graph.attribute.ReturnType;
import silverchain.data.graph.attribute.TypeArgument;
import silverchain.data.graph.attribute.TypeDeclaration;
import silverchain.data.graph.attribute.TypeParameter;
import silverchain.data.graph.attribute.TypeReference;
import silverchain.data.graph.attribute.Wildcard;
import silverchain.data.graph.attribute.collection.Bounds;
import silverchain.data.graph.attribute.collection.Exceptions;
import silverchain.data.graph.attribute.collection.Parameters;
import silverchain.data.graph.attribute.collection.TypeArguments;
import silverchain.data.graph.attribute.collection.TypeParameters;

@API(status = API.Status.INTERNAL)
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
    name.qualifier(build(ctx.qualifier()));
    return name;
  }

  public static Parameter build(ParamContext ctx) {
    if (ctx == null) return null;
    Parameter parameter = new Parameter();
    parameter.type(build(ctx.typeRef()));
    parameter.varargs(ctx.ELLIPSIS() != null);
    parameter.name(ctx.ID().getText());
    return parameter;
  }

  public static Qualifier build(QualifierContext ctx) {
    if (ctx == null) return null;
    Qualifier qualifier = new Qualifier();
    ctx.ID().stream().map(ParseTree::getText).forEach(qualifier.ids()::add);
    return qualifier;
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
    typeDeclaration.externalParameters(build(ctx.externalParams()));
    typeDeclaration.internalParameters(build(ctx.internalParams()));
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
    wildcard.upperBound(ctx.EXTENDS() != null);
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

  public static TypeParameters build(ExternalParamsContext ctx) {
    if (ctx == null) return new TypeParameters();
    return build(ctx.typeParams());
  }

  public static TypeParameters build(InternalParamsContext ctx) {
    if (ctx == null) return new TypeParameters();
    return build(ctx.typeParams());
  }
}
