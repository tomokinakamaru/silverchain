package silverchain.graph.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.graph.walker.AttrListener;
import silverchain.graph.walker.AttrVisitor;

@API(status = API.Status.INTERNAL)
public class Type implements Attr {

  private Name name;

  private TypeParams outerParams = new TypeParams();

  private TypeParams innerParams = new TypeParams();

  private Name originalName;

  public Name name() {
    return name;
  }

  public void name(Name name) {
    this.name = name;
  }

  public TypeParams outerParams() {
    return outerParams;
  }

  public void outerParams(TypeParams outerParams) {
    this.outerParams = outerParams;
  }

  public TypeParams innerParams() {
    return innerParams;
  }

  public void innerParams(TypeParams innerParams) {
    this.innerParams = innerParams;
  }

  public Name originalName() {
    return originalName;
  }

  public void originalName(Name originalName) {
    this.originalName = originalName;
  }

  @Override
  public Stream<? extends Attr> children() {
    return Stream.of(name, outerParams, innerParams).filter(Objects::nonNull);
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
