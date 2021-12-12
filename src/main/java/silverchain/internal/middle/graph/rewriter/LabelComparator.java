package silverchain.internal.middle.graph.rewriter;

import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;
import silverchain.internal.middle.data.AttributeVisitor;
import silverchain.internal.middle.data.attribute.Attribute;
import silverchain.internal.middle.data.attribute.Label;
import silverchain.internal.middle.data.attribute.Method;
import silverchain.internal.middle.data.attribute.Name;
import silverchain.internal.middle.data.attribute.Parameter;
import silverchain.internal.middle.data.attribute.TypeParameter;
import silverchain.internal.middle.data.attribute.TypeReference;
import silverchain.internal.middle.data.attribute.Wildcard;

public class LabelComparator implements AttributeVisitor<Boolean, Attribute> {

  private static final LabelComparator COMPARATOR = new LabelComparator();

  private LabelComparator() {}

  static boolean equals(Label label1, Label label2) {
    return equals((Attribute) label1, label2);
  }

  private static boolean equals(Attribute attribute1, Attribute attribute2) {
    if (attribute1 == attribute2) return true;
    if (attribute1 == null || attribute2 == null) return false;
    if (attribute1.getClass() != attribute2.getClass()) return false;
    return attribute1.accept(COMPARATOR, attribute2);
  }

  @Override
  public Boolean visit(Method method, Attribute arg) {
    Method m = (Method) arg;
    return Objects.equals(method.name(), m.name()) && AttributeVisitor.super.visit(method, arg);
  }

  @Override
  public Boolean visit(Name name, Attribute arg) {
    Name n = (Name) arg;
    return Objects.equals(name.id(), n.id()) && AttributeVisitor.super.visit(name, arg);
  }

  @Override
  public Boolean visit(Parameter parameter, Attribute arg) {
    Parameter p = (Parameter) arg;
    return Objects.equals(parameter.name(), p.name())
        && parameter.varargs() == p.varargs()
        && AttributeVisitor.super.visit(parameter, arg);
  }

  @Override
  public Boolean visit(TypeParameter typeParameter, Attribute arg) {
    TypeParameter t = (TypeParameter) arg;
    return Objects.equals(typeParameter.name(), t.name())
        && AttributeVisitor.super.visit(typeParameter, arg);
  }

  @Override
  public Boolean visit(TypeReference typeReference, Attribute arg) {
    TypeReference t = (TypeReference) arg;
    return typeReference.dimension() == t.dimension()
        && AttributeVisitor.super.visit(typeReference, arg);
  }

  @Override
  public Boolean visit(Wildcard wildcard, Attribute arg) {
    Wildcard w = (Wildcard) arg;
    return wildcard.upperBound() == w.upperBound() && AttributeVisitor.super.visit(wildcard, arg);
  }

  @Override
  public Boolean visit(Stream<? extends Attribute> attributes, Attribute arg) {
    Iterator<? extends Attribute> i1 = attributes.iterator();
    Iterator<? extends Attribute> i2 = arg.children().iterator();
    while (i1.hasNext() && i2.hasNext()) {
      Attribute a1 = i1.next();
      Attribute a2 = i2.next();
      if (!equals(a1, a2)) return false;
    }
    return i1.hasNext() == i2.hasNext();
  }
}
