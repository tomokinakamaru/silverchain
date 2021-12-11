package silverchain.internal.middle.graph.validator;

import java.util.stream.Collectors;
import silverchain.internal.middle.graph.ir.AttributeVisitor;
import silverchain.internal.middle.graph.ir.attribute.Method;
import silverchain.internal.middle.graph.ir.attribute.Name;
import silverchain.internal.middle.graph.ir.attribute.Parameter;
import silverchain.internal.middle.graph.ir.attribute.ReturnType;
import silverchain.internal.middle.graph.ir.attribute.TypeParameter;
import silverchain.internal.middle.graph.ir.attribute.TypeReference;
import silverchain.internal.middle.graph.ir.attribute.Wildcard;
import silverchain.internal.middle.graph.ir.attribute.collection.Bounds;
import silverchain.internal.middle.graph.ir.attribute.collection.Exceptions;
import silverchain.internal.middle.graph.ir.attribute.collection.Parameters;
import silverchain.internal.middle.graph.ir.attribute.collection.TypeArguments;
import silverchain.internal.middle.graph.ir.attribute.collection.TypeParameters;

public class LabelStringifier implements AttributeVisitor<String, Void> {

  public LabelStringifier() {}

  @Override
  public String visit(Method method, Void arg) {
    return AttributeVisitor.super.visit(method, arg);
  }

  @Override
  public String visit(Name name, Void arg) {
    Name q = name.qualifier();
    return q == null ? name.id() : q.accept(this, arg) + "." + name.id();
  }

  @Override
  public String visit(Parameter parameter, Void arg) {
    String s = AttributeVisitor.super.visit(parameter, arg);
    return s + (parameter.varargs() ? "... " : " ") + parameter.name();
  }

  @Override
  public String visit(ReturnType type, Void arg) {
    TypeReference r = type.type();
    return r == null ? "void " : r.accept(this, arg) + " ";
  }

  @Override
  public String visit(TypeParameter parameter, Void arg) {
    return parameter.name() + AttributeVisitor.super.visit(parameter, arg);
  }

  @Override
  public String visit(TypeReference reference, Void arg) {
    StringBuilder s = new StringBuilder(AttributeVisitor.super.visit(reference, arg));
    for (int i = 0; i < reference.dimension(); i++) s.append("[]");
    return s.toString();
  }

  @Override
  public String visit(Wildcard wildcard, Void arg) {
    if (wildcard.bound() == null) {
      return "?";
    } else {
      String s = wildcard.upperBound() ? " extends " : " super ";
      String t = wildcard.bound().accept(this, arg);
      return "?" + s + t;
    }
  }

  @Override
  public String visit(Bounds bounds, Void arg) {
    String s = bounds.stream().map(b -> b.accept(this, arg)).collect(Collectors.joining(" & "));
    return " extends " + s;
  }

  @Override
  public String visit(Exceptions exceptions, Void arg) {
    String s = exceptions.stream().map(e -> e.accept(this, arg)).collect(Collectors.joining(", "));
    return " throws " + s;
  }

  @Override
  public String visit(Parameters parameters, Void arg) {
    String s = parameters.stream().map(p -> p.accept(this, arg)).collect(Collectors.joining(", "));
    return "(" + s + ")";
  }

  @Override
  public String visit(TypeArguments arguments, Void arg) {
    String s = arguments.stream().map(a -> a.accept(this, arg)).collect(Collectors.joining(", "));
    return "<" + s + ">";
  }

  @Override
  public String visit(TypeParameters parameters, Void arg) {
    String s = parameters.stream().map(p -> p.accept(this, arg)).collect(Collectors.joining(", "));
    return "<" + s + ">";
  }

  @Override
  public String aggregate(String result1, String result2) {
    return result1 + result2;
  }

  @Override
  public String defaultResult(Void arg) {
    return "";
  }
}
