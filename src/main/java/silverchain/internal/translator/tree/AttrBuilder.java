package silverchain.internal.translator.tree;

import java.util.stream.Collectors;
import org.antlr.v4.runtime.Token;
import silverchain.internal.front.parser.AgParser.BoundsContext;
import silverchain.internal.front.parser.AgParser.ExceptionsContext;
import silverchain.internal.front.parser.AgParser.MethodContext;
import silverchain.internal.front.parser.AgParser.NameContext;
import silverchain.internal.front.parser.AgParser.ParamContext;
import silverchain.internal.front.parser.AgParser.ParamsContext;
import silverchain.internal.front.parser.AgParser.ReturnTypeContext;
import silverchain.internal.front.parser.AgParser.TypeArgContext;
import silverchain.internal.front.parser.AgParser.TypeArgsContext;
import silverchain.internal.front.parser.AgParser.TypeDeclContext;
import silverchain.internal.front.parser.AgParser.TypeParamContext;
import silverchain.internal.front.parser.AgParser.TypeParamsContext;
import silverchain.internal.front.parser.AgParser.TypeRefContext;
import silverchain.internal.front.parser.AgParser.WildcardContext;
import silverchain.internal.front.rewriter.VirtualToken;
import silverchain.internal.middle.graph.data.attribute.Method;
import silverchain.internal.middle.graph.data.attribute.Name;
import silverchain.internal.middle.graph.data.attribute.Parameter;
import silverchain.internal.middle.graph.data.attribute.ReturnType;
import silverchain.internal.middle.graph.data.attribute.TypeArgument;
import silverchain.internal.middle.graph.data.attribute.TypeDeclaration;
import silverchain.internal.middle.graph.data.attribute.TypeParameter;
import silverchain.internal.middle.graph.data.attribute.TypeReference;
import silverchain.internal.middle.graph.data.attribute.Wildcard;
import silverchain.internal.middle.graph.data.attribute.collection.Bounds;
import silverchain.internal.middle.graph.data.attribute.collection.Exceptions;
import silverchain.internal.middle.graph.data.attribute.collection.Parameters;
import silverchain.internal.middle.graph.data.attribute.collection.TypeArguments;
import silverchain.internal.middle.graph.data.attribute.collection.TypeParameters;
import silverchain.internal.middle.graph.data.location.Location;
import silverchain.internal.middle.graph.data.location.Locations;

public final class AttrBuilder {

  private AttrBuilder() {}

  public static Method build(MethodContext ctx) {
    if (ctx == null) return null;
    Method method = new Method();
    method.name(ctx.ID().getText());
    method.typeParameters(build(ctx.typeParams()));
    method.parameters(build(ctx.params()));
    method.exceptions(build(ctx.exceptions()));
    method.locations(build(ctx.start));
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
    returnType.locations(build(ctx.start));
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
    typeDeclaration.externalParameters(build(ctx.eParams));
    typeDeclaration.internalParameters(build(ctx.iParams));
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
        .map(AttrBuilder::build)
        .collect(Collectors.toCollection(Bounds::new));
  }

  public static Exceptions build(ExceptionsContext ctx) {
    if (ctx == null) return new Exceptions();
    return ctx.typeRef().stream()
        .map(AttrBuilder::build)
        .collect(Collectors.toCollection(Exceptions::new));
  }

  public static Parameters build(ParamsContext ctx) {
    if (ctx == null) return new Parameters();
    return ctx.param().stream()
        .map(AttrBuilder::build)
        .collect(Collectors.toCollection(Parameters::new));
  }

  public static TypeArguments build(TypeArgsContext ctx) {
    if (ctx == null) return new TypeArguments();
    return ctx.typeArg().stream()
        .map(AttrBuilder::build)
        .collect(Collectors.toCollection(TypeArguments::new));
  }

  public static TypeParameters build(TypeParamsContext ctx) {
    if (ctx == null) return new TypeParameters();
    return ctx.typeParam().stream()
        .map(AttrBuilder::build)
        .collect(Collectors.toCollection(TypeParameters::new));
  }

  public static Locations build(Token token) {
    Locations locations = new Locations();
    Location location = new Location();
    location.line(token.getLine());
    location.column(token.getCharPositionInLine());
    if (token instanceof VirtualToken) {
      VirtualToken v = (VirtualToken) token;
      locations.addAll(build(v.subToken()));
    }
    return locations;
  }
}
