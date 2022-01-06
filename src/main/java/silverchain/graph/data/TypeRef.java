package silverchain.graph.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.ag.data.TypeRefTree;
import silverchain.graph.core.AttrImpl;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class TypeRef extends AttrImpl implements TypeArg {

  private Name name;

  private TypeArgs args;

  private int dim;

  private Name originalName;

  private TypeParam target;

  public static TypeRef build(TypeRefTree tree) {
    if (tree == null) return null;
    TypeRef attr = new TypeRef();
    attr.name(Name.build(tree.name()));
    attr.args(TypeArgs.build(tree.args()));
    attr.dim(tree.dim());
    attr.originalName(Name.build(tree.originalName()));
    attr.srcMap().add(tree.srcMap());
    return attr;
  }

  public Name name() {
    return name;
  }

  public void name(Name name) {
    this.name = name;
  }

  public TypeArgs args() {
    return args;
  }

  public void args(TypeArgs args) {
    this.args = args;
  }

  public int dim() {
    return dim;
  }

  public void dim(int dim) {
    this.dim = dim;
  }

  public Name originalName() {
    return originalName;
  }

  public void originalName(Name originalName) {
    this.originalName = originalName;
  }

  public TypeParam target() {
    return target;
  }

  public void target(TypeParam target) {
    this.target = target;
  }

  @Override
  public Stream<? extends AttrImpl> children() {
    return Stream.of(name, args).filter(Objects::nonNull);
  }

  @Override
  public <T> void enter(AttrListener<T> listener, T arg) {
    listener.enter(this, arg);
  }

  @Override
  public <T> void exit(AttrListener<T> listener, T arg) {
    listener.exit(this, arg);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(originalName + Objects.toString(args, ""));
    for (int i = 0; i < dim; i++) builder.append("[]");
    return builder.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TypeRef typeRef = (TypeRef) o;
    return dim == typeRef.dim
        && Objects.equals(name, typeRef.name)
        && Objects.equals(args, typeRef.args);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, args, dim, originalName, target);
  }
}
