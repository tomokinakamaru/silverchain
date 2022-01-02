package silverchain.graph;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.ag.data.BoundTree;
import silverchain.ag.data.BoundsTree;
import silverchain.ag.data.ExceptionsTree;
import silverchain.ag.data.InnerParamsTree;
import silverchain.ag.data.MethodTree;
import silverchain.ag.data.NameTree;
import silverchain.ag.data.OuterParamsTree;
import silverchain.ag.data.ParamTree;
import silverchain.ag.data.ParamsTree;
import silverchain.ag.data.TypeArgTree;
import silverchain.ag.data.TypeArgsTree;
import silverchain.ag.data.TypeDeclTree;
import silverchain.ag.data.TypeParamTree;
import silverchain.ag.data.TypeParamsTree;
import silverchain.ag.data.TypeRefTree;
import silverchain.ag.data.WildcardTree;
import silverchain.graph.data.Bound;
import silverchain.graph.data.Bounds;
import silverchain.graph.data.Exceptions;
import silverchain.graph.data.Method;
import silverchain.graph.data.Name;
import silverchain.graph.data.Param;
import silverchain.graph.data.Params;
import silverchain.graph.data.Type;
import silverchain.graph.data.TypeArg;
import silverchain.graph.data.TypeArgs;
import silverchain.graph.data.TypeParam;
import silverchain.graph.data.TypeParams;
import silverchain.graph.data.TypeRef;
import silverchain.graph.data.Wildcard;

@API(status = API.Status.INTERNAL)
public final class AttrBuilder {

  private AttrBuilder() {}

  public static Method build(MethodTree tree) {
    if (tree == null) return null;
    Method method = new Method();
    method.name(tree.name());
    method.typeParams(build(tree.typeParams()));
    method.params(build(tree.params()));
    method.exceptions(build(tree.exceptions()));
    method.srcMap().add(tree.srcMap());
    return method;
  }

  public static Name build(NameTree tree) {
    if (tree == null) return null;
    Name name = new Name();
    name.id(tree.id());
    name.qualifier(tree.qualifier().stream().map(s -> s + ".").collect(Collectors.joining()));
    return name;
  }

  public static Param build(ParamTree tree) {
    if (tree == null) return null;
    Param param = new Param();
    param.type(build(tree.type()));
    param.varargs(tree.varargs());
    param.name(tree.name());
    return param;
  }

  public static TypeArg build(TypeArgTree<?> tree) {
    if (tree == null) return null;
    return tree instanceof WildcardTree ? build((WildcardTree) tree) : build((TypeRefTree) tree);
  }

  public static Type build(TypeDeclTree tree) {
    if (tree == null) return null;
    Type type = new Type();
    type.name(build(tree.head().name()));
    type.outerParams(build(tree.head().params().outerParams()));
    type.innerParams(build(tree.head().params().innerParams()));
    type.originalName(build(tree.head().originalName()));
    return type;
  }

  public static TypeParam build(TypeParamTree tree) {
    if (tree == null) return null;
    TypeParam typeParam = new TypeParam();
    typeParam.name(tree.name());
    typeParam.bounds(build(tree.bounds()));
    return typeParam;
  }

  public static TypeRef build(TypeRefTree tree) {
    if (tree == null) return null;
    TypeRef typeRef = new TypeRef();
    typeRef.name(build(tree.name()));
    typeRef.args(build(tree.args()));
    typeRef.dim(tree.dim());
    typeRef.originalName(build(tree.originalName()));
    return typeRef;
  }

  public static Wildcard build(WildcardTree tree) {
    if (tree == null) return null;
    Wildcard wildcard = new Wildcard();
    wildcard.bound(build(tree.bound()));
    return wildcard;
  }

  public static Bound build(BoundTree tree) {
    if (tree == null) return null;
    Bound bound = new Bound();
    bound.upperBound(tree.upperBound());
    bound.type(build(tree.type()));
    return bound;
  }

  public static Bounds build(BoundsTree tree) {
    if (tree == null) return new Bounds();
    return tree.stream().map(AttrBuilder::build).collect(Collectors.toCollection(Bounds::new));
  }

  public static Exceptions build(ExceptionsTree tree) {
    if (tree == null) return new Exceptions();
    return tree.stream().map(AttrBuilder::build).collect(Collectors.toCollection(Exceptions::new));
  }

  public static Params build(ParamsTree tree) {
    if (tree == null) return new Params();
    return tree.stream().map(AttrBuilder::build).collect(Collectors.toCollection(Params::new));
  }

  public static TypeArgs build(TypeArgsTree tree) {
    if (tree == null) return new TypeArgs();
    return tree.stream().map(AttrBuilder::build).collect(Collectors.toCollection(TypeArgs::new));
  }

  public static TypeParams build(TypeParamsTree tree) {
    if (tree == null) return new TypeParams();
    return build(tree.stream());
  }

  public static TypeParams build(OuterParamsTree tree) {
    if (tree == null) return new TypeParams();
    return build(tree.stream());
  }

  public static TypeParams build(InnerParamsTree tree) {
    if (tree == null) return new TypeParams();
    return build(tree.stream());
  }

  private static TypeParams build(Stream<TypeParamTree> stream) {
    return stream.map(AttrBuilder::build).collect(Collectors.toCollection(TypeParams::new));
  }
}
