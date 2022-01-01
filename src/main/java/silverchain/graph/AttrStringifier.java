package silverchain.graph;

import java.util.Set;
import java.util.stream.Collectors;
import org.apiguardian.api.API;
import silverchain.graph.data.Attrs;
import silverchain.graph.data.Bound;
import silverchain.graph.data.Bounds;
import silverchain.graph.data.EdgeAttr;
import silverchain.graph.data.Exceptions;
import silverchain.graph.data.Method;
import silverchain.graph.data.Name;
import silverchain.graph.data.Param;
import silverchain.graph.data.Params;
import silverchain.graph.data.RetType;
import silverchain.graph.data.TypeArgs;
import silverchain.graph.data.TypeParam;
import silverchain.graph.data.TypeParams;
import silverchain.graph.data.TypeRef;
import silverchain.graph.data.Wildcard;
import silverchain.graph.walker.AttrVisitor;

@API(status = API.Status.INTERNAL)
public final class AttrStringifier implements AttrVisitor<String, Void> {

  private static final AttrStringifier STRINGIFIER = new AttrStringifier();

  private AttrStringifier() {}

  public static String stringify(Set<EdgeAttr> attrs) {
    return attrs.stream()
        .map(a -> a.accept(STRINGIFIER, null))
        .sorted()
        .collect(Collectors.joining("; "));
  }

  @Override
  public String visit(Method method, Void arg) {
    String s = "@" + method.intervals().toString();
    return method.name() + visitChildren(method, arg) + s;
  }

  @Override
  public String visit(Name name, Void arg) {
    return visitChildren(name, arg) + name.id();
  }

  @Override
  public String visit(Param param, Void arg) {
    String s = param.varargs() ? "... " : " ";
    return visitChildren(param, arg) + s + param.name();
  }

  @Override
  public String visit(RetType retType, Void arg) {
    String s = "@" + retType.intervals().toString();
    return visitChildren(retType, arg) + s;
  }

  @Override
  public String visit(TypeParam typeParam, Void arg) {
    return typeParam.name() + visitChildren(typeParam, arg);
  }

  @Override
  public String visit(TypeRef typeRef, Void arg) {
    StringBuilder s = new StringBuilder(visitChildren(typeRef, arg));
    for (int i = 0; i < typeRef.dim(); i++) s.append("[]");
    return s.toString();
  }

  @Override
  public String visit(Wildcard wildcard, Void arg) {
    return "?" + visitChildren(wildcard, arg);
  }

  @Override
  public String visit(Bound bound, Void arg) {
    String s = bound.upperBound() ? " extends " : " super ";
    return s + visitChildren(bound, arg);
  }

  @Override
  public String visit(Bounds bounds, Void arg) {
    return " extends " + join(" & ", bounds);
  }

  @Override
  public String visit(Exceptions exceptions, Void arg) {
    if (exceptions.isEmpty()) return "";
    return " throws " + join(", ", exceptions);
  }

  @Override
  public String visit(Params params, Void arg) {
    return "(" + join(", ", params) + ")";
  }

  @Override
  public String visit(TypeArgs typeArgs, Void arg) {
    if (typeArgs.isEmpty()) return "";
    return "<" + join(", ", typeArgs) + ">";
  }

  @Override
  public String visit(TypeParams typeParams, Void arg) {
    if (typeParams.isEmpty()) return "";
    return "<" + join(", ", typeParams) + ">";
  }

  @Override
  public String aggregate(String result1, String result2) {
    return result1 + result2;
  }

  @Override
  public String defaultResult(Void arg) {
    return "";
  }

  private static String join(String delimiter, Attrs<?> attrs) {
    return attrs.stream()
        .map(a -> a.accept(STRINGIFIER, null))
        .collect(Collectors.joining(delimiter));
  }
}
