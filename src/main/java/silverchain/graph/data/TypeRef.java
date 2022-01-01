package silverchain.graph.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.graph.walker.AttrListener;
import silverchain.graph.walker.AttrVisitor;

@API(status = API.Status.INTERNAL)
public class TypeRef extends TypeArg implements EdgeAttr {

  private Name name;

  private TypeArgs args = new TypeArgs();

  private int dim;

  private Name originalName;

  private TypeParam target;

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
  public Stream<? extends Attr> children() {
    return Stream.of(name, args).filter(Objects::nonNull);
  }

  @Override
  public <R, A> R accept(AttrVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }

  @Override
  public void enter(AttrListener listener) {
    listener.enter(this);
  }

  @Override
  public void exit(AttrListener listener) {
    listener.exit(this);
  }
}
