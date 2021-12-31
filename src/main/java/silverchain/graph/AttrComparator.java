package silverchain.graph;

import java.util.Iterator;
import java.util.Objects;
import org.apiguardian.api.API;
import silverchain.graph.data.Attr;
import silverchain.graph.data.Bound;
import silverchain.graph.data.Method;
import silverchain.graph.data.Name;
import silverchain.graph.data.Param;
import silverchain.graph.data.TypeParam;
import silverchain.graph.data.TypeRef;
import silverchain.graph.walker.AttrVisitor;

@API(status = API.Status.INTERNAL)
public final class AttrComparator implements AttrVisitor<Boolean, Attr> {

  private static final AttrComparator COMPARATOR = new AttrComparator();

  private AttrComparator() {}

  public static boolean equals(Attr attr1, Attr attr2) {
    if (attr1 == attr2) return true;
    if (attr1 == null || attr2 == null) return false;
    if (attr1.getClass() != attr2.getClass()) return false;
    return attr1.accept(COMPARATOR, attr2);
  }

  @Override
  public Boolean visit(Method method, Attr arg) {
    Method m = (Method) arg;
    return Objects.equals(method.name(), m.name()) && visitChildren(method, arg);
  }

  @Override
  public Boolean visit(Name name, Attr arg) {
    Name n = (Name) arg;
    return Objects.equals(name.id(), n.id()) && visitChildren(name, arg);
  }

  @Override
  public Boolean visit(Param param, Attr arg) {
    Param p = (Param) arg;
    return param.varargs() == p.varargs()
        && Objects.equals(param.name(), p.name())
        && visitChildren(param, arg);
  }

  @Override
  public Boolean visit(TypeParam typeParam, Attr arg) {
    TypeParam t = (TypeParam) arg;
    return Objects.equals(typeParam.name(), t.name()) && visitChildren(typeParam, arg);
  }

  @Override
  public Boolean visit(TypeRef typeRef, Attr arg) {
    TypeRef t = (TypeRef) arg;
    return typeRef.dim() == t.dim() && visitChildren(typeRef, arg);
  }

  @Override
  public Boolean visit(Bound bound, Attr arg) {
    Bound w = (Bound) arg;
    return bound.upperBound() == w.upperBound() && visitChildren(bound, arg);
  }

  @Override
  public Boolean visitChildren(Attr attr, Attr arg) {
    Iterator<? extends Attr> i1 = attr.children().iterator();
    Iterator<? extends Attr> i2 = arg.children().iterator();
    while (i1.hasNext() && i2.hasNext()) {
      Attr a1 = i1.next();
      Attr a2 = i2.next();
      if (!equals(a1, a2)) return false;
    }
    return i1.hasNext() == i2.hasNext();
  }
}
